package xyz.wochib70.domain.inventory.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.InventoryType;

public record CreateInventoryCmd(
        IdentifierId<Long> goodsId,
        GoodsType goodsType,
        InventoryType inventoryType,
        Integer quantity
) {
}
