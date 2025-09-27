package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityCountLimit;

public record ModifyActivityCountLimitCmd(
        IdentifierId<Long> activityId,
        ActivityCountLimit countLimit
) {
}
