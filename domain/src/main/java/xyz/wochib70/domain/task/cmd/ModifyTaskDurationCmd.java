package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskDuration;

public record ModifyTaskDurationCmd(
        IdentifierId<Long> taskId,
        TaskDuration duration
) {
}
