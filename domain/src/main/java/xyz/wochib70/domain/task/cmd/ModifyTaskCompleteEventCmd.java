package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.CompleteEvent;

public record ModifyTaskCompleteEventCmd(
        IdentifierId<Long> taskId,
        CompleteEvent completeEvent
) {
}
