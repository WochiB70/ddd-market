package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public record ParticipateActivityWithRedeemCmd(
        IdentifierId<Long> activityId,
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        Integer count,
        UserId userId,
        String credentialUsageCode

) {
}
