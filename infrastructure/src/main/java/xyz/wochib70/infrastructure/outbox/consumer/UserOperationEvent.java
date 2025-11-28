package xyz.wochib70.infrastructure.outbox.consumer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.usertask.cmd.ReceiveAndCompleteTaskByEventCmd;

public record UserOperationEvent(
        @NotBlank
        String userId,
        @NotNull
        CompleteEvent completeEvent
) {


    public ReceiveAndCompleteTaskByEventCmd toCmd() {
        return new ReceiveAndCompleteTaskByEventCmd(new UserId(Long.parseLong(userId)), completeEvent);
    }
}
