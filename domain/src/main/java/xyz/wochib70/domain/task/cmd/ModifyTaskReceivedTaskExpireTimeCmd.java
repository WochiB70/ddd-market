package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;

public record ModifyTaskReceivedTaskExpireTimeCmd(
        IdentifierId<Long> taskId,
        ReceivedTaskExpireTime receivedTaskExpireTime
) {
}
