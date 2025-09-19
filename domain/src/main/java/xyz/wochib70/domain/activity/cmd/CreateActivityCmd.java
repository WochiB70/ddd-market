package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityInfo;
import xyz.wochib70.domain.activity.CountLimit;

public record CreateActivityCmd(
        ActivityInfo info,
        ActivityDuration duration,
        CountLimit countLimit,
        Boolean credentialLimit,
        ActivityAwardType awardType
) {


}
