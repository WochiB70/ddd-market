package xyz.wochib70.domain.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryFactory {

    private final InventoryIdGenerator inventoryIdGenerator;

    public Inventory createInventory(
            IdentifierId<Long> goodsId,
            GoodsType goodsType,
            InventoryType inventoryType,
            Integer count
    ) {
        ParameterUtil.requireNonNull(goodsId, "商品id不能为空");
        ParameterUtil.requireNonNull(goodsType, "商品类型不能为空");
        ParameterUtil.requireNonNull(inventoryType, "库存类型不能为空");
        ParameterUtil.requireExpression(inventoryType == InventoryType.LIMITED && (count == null || count < 0),
                "库存类型为LIMITED， 库存数量不能为空 且 库存数量不能小于0");

        InventoryImpl inventory = new InventoryImpl(inventoryIdGenerator.nextInventoryId());
        inventory.setGoodsId(goodsId);
        inventory.setGoodsType(goodsType);
        inventory.setType(inventoryType);
        inventory.setCount(count);
        return inventory;
    }
}
