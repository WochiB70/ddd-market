package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.*;

public record CreateTaskCmd(
        IdentifierId<Long> activityId,
        TaskInfo info,
        TaskCountLimit taskCountLimit,
        TaskDuration duration,
        ReceivedTaskExpireTime receivedTaskExpireTime,
        CompleteEvent completeEvent,
        TaskAward award
) {
}
