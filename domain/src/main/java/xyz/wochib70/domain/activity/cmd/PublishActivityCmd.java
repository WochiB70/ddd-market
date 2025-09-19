package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;

public record PublishActivityCmd(
        IdentifierId<Long> activityId
) {
}
