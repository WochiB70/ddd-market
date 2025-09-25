package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskReceivedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final UserId userId;

    public TaskReceivedEvent(IdentifierId<Long> taskId, UserId userId) {
        super(TaskImpl.class, TaskReceivedEvent.class);
        this.taskId = taskId;
        this.userId = userId;
    }
}
