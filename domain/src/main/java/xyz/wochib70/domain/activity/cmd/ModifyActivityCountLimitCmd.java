package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.CountLimit;

public record ModifyActivityCountLimitCmd(
        IdentifierId<Long> activityId,
        CountLimit countLimit
) {
}
