package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record DecreaseRedeemItemInventoryCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        Integer decrease
) {
}
