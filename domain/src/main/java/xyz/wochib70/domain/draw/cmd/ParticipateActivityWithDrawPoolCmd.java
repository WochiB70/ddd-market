package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public record ParticipateActivityWithDrawPoolCmd(
        IdentifierId<Long> activityId,
        IdentifierId<Long> drawPoolId,
        UserId userId,
        String credentialUsageCode
) {
}
