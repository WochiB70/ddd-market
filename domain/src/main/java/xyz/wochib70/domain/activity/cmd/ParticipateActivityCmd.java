package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public record ParticipateActivityCmd(
        IdentifierId<Long> activityId,
        UserId userId
) {
}
