package xyz.wochib70.domain.usertask.cmd;

import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.CompleteEvent;

public record ReceiveAndCompleteTaskByEventCmd(
        UserId userId,
        CompleteEvent completeEvent
) {

    public ReceiveAndCompleteTaskByEventCmd {
        if (completeEvent == null) {
            throw new IllegalArgumentException("completeEvent cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
    }
}
