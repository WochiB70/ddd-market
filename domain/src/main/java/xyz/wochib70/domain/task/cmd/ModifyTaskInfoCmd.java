package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskInfo;

public record ModifyTaskInfoCmd(
        IdentifierId<Long> taskId,
        TaskInfo info
) {
}
