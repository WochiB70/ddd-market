package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityAwardType;

public record ModifyActivityAwardTypeCmd(
        IdentifierId<Long> activityId,
        ActivityAwardType awardType
) {
}
