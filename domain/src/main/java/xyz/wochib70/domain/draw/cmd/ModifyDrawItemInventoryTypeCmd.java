package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.InventoryType;

public record ModifyDrawItemInventoryTypeCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId,
        InventoryType inventoryType
) {
}
