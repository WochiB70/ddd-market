package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemParticipateScope;

public record CreateRedeemCmd(
        IdentifierId<Long> activityId,
        String name,
        RedeemParticipateScope scope
) {
}
