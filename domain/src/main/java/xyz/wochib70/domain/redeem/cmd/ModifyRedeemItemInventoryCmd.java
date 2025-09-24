package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemItemInventory;

public record ModifyRedeemItemInventoryCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        RedeemItemInventory inventory
) {
}
