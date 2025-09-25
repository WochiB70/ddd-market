package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;
import xyz.wochib70.domain.task.TaskCountLimit;
import xyz.wochib70.domain.task.TaskDuration;
import xyz.wochib70.domain.task.TaskInfo;

public record CreateTaskCmd(
        IdentifierId<Long> activityId,
        TaskInfo info,
        TaskCountLimit taskCountLimit,
        TaskDuration duration,
        ReceivedTaskExpireTime receivedTaskExpireTime
) {
}
