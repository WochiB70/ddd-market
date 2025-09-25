package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskEnabledEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    public TaskEnabledEvent(IdentifierId<Long> taskId) {
        super(TaskImpl.class, TaskEnabledEvent.class);
        this.taskId = taskId;
    }
}
