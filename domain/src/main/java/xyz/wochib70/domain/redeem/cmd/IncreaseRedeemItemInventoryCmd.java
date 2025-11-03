package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record IncreaseRedeemItemInventoryCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        Integer increase
) {
}
