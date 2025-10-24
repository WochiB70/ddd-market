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
import xyz.wochib70.infrastructure.draw.QDrawItemEntity;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "抽奖池物品查询接口", description = "提供抽奖池物品信息的查询接口")
public class QueryDrawPoolItemController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/draw-pool-item/{drawPoolId}")
    @Operation(summary = "根据抽奖池ID查询物品列表", description = "根据抽奖池ID获取该抽奖池下的所有物品信息")
    @ApiResponse(responseCode = "200", description = "成功返回抽奖池物品列表",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryDrawPoolItemResponse.class)))
    public List<QueryDrawPoolItemResponse> queryDrawPoolItem(
            @Parameter(description = "抽奖池ID", example = "1")
            @PathVariable("drawPoolId") Long drawPoolId
    ) {

        QDrawItemEntity drawItem = QDrawItemEntity.drawItemEntity;

        return queryFactory.select(
                        drawItem.id,
                        drawItem.name,
                        drawItem.description,
                        drawItem.type,
                        drawItem.weight,
                        drawItem.inventoryType,
                        drawItem.inventorySurplus
                )
                .from(drawItem)
                .where(drawItem.drawPoolId.eq(drawPoolId))
                .fetch()
                .stream()
                .map(item -> {
                    QueryDrawPoolItemResponse response = new QueryDrawPoolItemResponse();
                    response.setId(item.get(drawItem.id));
                    response.setName(item.get(drawItem.name));
                    response.setDescription(item.get(drawItem.description));
                    response.setType(item.get(drawItem.type));
                    response.setWeight(item.get(drawItem.weight));
                    response.setInventory(new QueryDrawPoolItemResponse.DrawInventory(
                            item.get(drawItem.inventoryType),
                            item.get(drawItem.inventorySurplus)
                    ));
                    response.setDrawPoolId(drawPoolId);
                    return response;
                })
                .toList();

    }
}