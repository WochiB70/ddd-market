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
import xyz.wochib70.infrastructure.draw.QDrawPoolEntity;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "抽奖池查询接口", description = "提供抽奖池信息的查询接口")
public class QueryDrawPoolController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/draw-pools/{activityId}")
    @Operation(summary = "根据活动ID查询抽奖池列表", description = "根据活动ID获取该活动下的所有抽奖池信息")
    @ApiResponse(responseCode = "200", description = "成功返回抽奖池列表",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryDrawPoolResponse.class)))
    public List<QueryDrawPoolResponse> queryDrawPools(
            @Parameter(description = "活动ID", example = "1")
            @PathVariable("activityId") Long activityId
    ) {
        QDrawPoolEntity drawPool = QDrawPoolEntity.drawPoolEntity;
        return queryFactory.select(
                        drawPool.id,
                        drawPool.name,
                        drawPool.strategyType,
                        drawPool.activityId,
                        drawPool.currencyId,
                        drawPool.price
                )
                .from(drawPool)
                .where(drawPool.activityId.eq(activityId))
                .fetch()
                .stream()
                .map(it -> {
                    QueryDrawPoolResponse response = new QueryDrawPoolResponse();
                    response.setId(it.get(drawPool.id));
                    response.setName(it.get(drawPool.name));
                    response.setStrategyType(it.get(drawPool.strategyType));
                    response.setActivityId(it.get(drawPool.activityId));
                    response.setDrawPrice(new QueryDrawPoolResponse.DrawPrice(
                            it.get(drawPool.currencyId),
                            it.get(drawPool.price))
                    );
                    return response;
                })
                .toList();


    }
}