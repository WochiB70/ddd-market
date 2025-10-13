package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityDuration;

public record ModifyActivityDurationCmd(
        IdentifierId<Long> activityId,
        ActivityDuration duration
) {
}
