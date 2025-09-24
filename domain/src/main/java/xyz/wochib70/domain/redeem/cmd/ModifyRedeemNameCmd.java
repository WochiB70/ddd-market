package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyRedeemNameCmd(
        IdentifierId<Long> redeemId,
        String name
) {
}
