package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskCountLimit;

public record ModifyTaskCountLimitCmd(
        IdentifierId<Long> taskId,
        TaskCountLimit countLimit
) {
}
