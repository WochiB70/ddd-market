package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskAward;

public record ModifyTaskAwardCmd(
        IdentifierId<Long> taskId,
        TaskAward taskAward
) {
}
