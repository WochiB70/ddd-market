package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record RemoveRedeemItemCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId
) {
}
