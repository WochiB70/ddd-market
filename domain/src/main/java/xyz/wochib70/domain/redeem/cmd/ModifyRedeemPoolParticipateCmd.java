package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemParticipateScope;

public record ModifyRedeemPoolParticipateCmd(
        IdentifierId<Long> redeemPoolId,
        RedeemParticipateScope participateScope
) {
}
