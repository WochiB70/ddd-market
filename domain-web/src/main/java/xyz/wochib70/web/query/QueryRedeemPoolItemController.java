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
import xyz.wochib70.infrastructure.redeem.QRedeemItemEntity;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "兑换池物品查询接口", description = "提供兑换池物品信息的查询接口")
public class QueryRedeemPoolItemController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/redeem-pool-item/{redeemPoolId}")
    @Operation(summary = "根据兑换池ID查询物品列表", description = "根据兑换池ID获取该兑换池下的所有物品信息")
    @ApiResponse(responseCode = "200", description = "成功返回兑换池物品列表",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryRedeemPoolItemResponse.class)))
    public List<QueryRedeemPoolItemResponse> queryRedeemPoolItem(
            @Parameter(description = "兑换池ID", example = "1")
            @PathVariable("redeemPoolId") Long redeemPoolId
    ) {
        QRedeemItemEntity redeemItem = QRedeemItemEntity.redeemItemEntity;
        return queryFactory.select(
                        redeemItem.id,
                        redeemItem.name,
                        redeemItem.description,
                        redeemItem.type,
                        redeemItem.currencyId,
                        redeemItem.price,
                        redeemItem.inventoryType,
                        redeemItem.validCount
                )
                .from(redeemItem)
                .where(redeemItem.redeemId.eq(redeemPoolId))
                .fetch()
                .stream()
                .map(item -> {
                    QueryRedeemPoolItemResponse response = new QueryRedeemPoolItemResponse();
                    response.setId(item.get(redeemItem.id));
                    response.setName(item.get(redeemItem.name));
                    response.setDescription(item.get(redeemItem.description));
                    response.setType(item.get(redeemItem.type));
                    response.setPrice(new QueryRedeemPoolItemResponse.RedeemPrice(
                            item.get(redeemItem.currencyId),
                            item.get(redeemItem.price)

                    ));
                    response.setInventory(new QueryRedeemPoolItemResponse.RedeemItemInventory(
                            item.get(redeemItem.inventoryType),
                            item.get(redeemItem.validCount)
                    ));
                    response.setRedeemPoolId(redeemPoolId);
                    return response;
                })
                .toList();

    }
}