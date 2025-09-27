package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityInfo;
import xyz.wochib70.domain.activity.ActivityCountLimit;

public record CreateActivityCmd(
        ActivityInfo info,
        ActivityDuration duration,
        ActivityCountLimit countLimit,
        Boolean credentialLimit,
        ActivityAwardType awardType
) {


}
