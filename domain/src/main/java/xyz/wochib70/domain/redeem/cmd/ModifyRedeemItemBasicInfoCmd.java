package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyRedeemItemBasicInfoCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        String name,
        String description
) {
}
