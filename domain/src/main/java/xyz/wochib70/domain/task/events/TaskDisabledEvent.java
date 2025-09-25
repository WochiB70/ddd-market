package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskDisabledEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    public TaskDisabledEvent(IdentifierId<Long> taskId) {
        super(TaskImpl.class, TaskDisabledEvent.class);
        this.taskId = taskId;
    }
}
