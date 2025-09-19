package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityInfo;

public record ModifyActivityInfoCmd(
        IdentifierId<Long> activityId,
        ActivityInfo info
) {
}
