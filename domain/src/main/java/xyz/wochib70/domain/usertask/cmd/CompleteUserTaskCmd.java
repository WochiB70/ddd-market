package xyz.wochib70.domain.usertask.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public record CompleteUserTaskCmd(
        IdentifierId<Long> userTaskId,
        UserId userId
) {
}
