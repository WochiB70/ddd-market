package xyz.wochib70.web.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.infrastructure.redeem.QRedeemEntity;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "兑换池查询接口", description = "提供兑换池信息的查询接口")
public class QueryRedeemPoolController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/redeem-pools/{activityId}")
    @Operation(summary = "根据活动ID查询兑换池列表", description = "根据活动ID获取该活动下的所有兑换池信息")
    @ApiResponse(responseCode = "200", description = "成功返回兑换池列表",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryRedeemPoolResponse.class)))
    public List<QueryRedeemPoolResponse> queryRedeemPools(
            @Parameter(description = "活动ID", example = "1")
            @PathVariable("activityId") Long activityId
    ) {
        QRedeemEntity redeem = QRedeemEntity.redeemEntity;
        return queryFactory.select(
                        redeem.id,
                        redeem.name
                )
                .from(redeem)
                .where(redeem.activityId.eq(activityId))
                .fetch()
                .stream()
                .map(tuple -> {
                    QueryRedeemPoolResponse response = new QueryRedeemPoolResponse();
                    response.setId(tuple.get(redeem.id));
                    response.setName(tuple.get(redeem.name));
                    response.setActivityId(activityId);
                    return response;
                })
                .toList();
    }

}