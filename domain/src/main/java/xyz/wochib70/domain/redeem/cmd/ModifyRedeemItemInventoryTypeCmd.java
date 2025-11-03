package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.InventoryType;

public record ModifyRedeemItemInventoryTypeCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        InventoryType inventoryType
) {
}
