package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskReceivedTaskExpireTimeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final ReceivedTaskExpireTime receivedTaskExpireTime;

    public TaskReceivedTaskExpireTimeModifiedEvent(
            IdentifierId<Long> taskId,
            ReceivedTaskExpireTime receivedTaskExpireTime
    ) {
        super(TaskImpl.class, TaskReceivedTaskExpireTimeModifiedEvent.class);
        this.taskId = taskId;
        this.receivedTaskExpireTime = receivedTaskExpireTime;
    }
}
