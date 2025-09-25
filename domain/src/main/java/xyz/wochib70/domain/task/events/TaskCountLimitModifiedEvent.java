package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskCountLimit;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskCountLimitModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final TaskCountLimit taskCountLimit;

    public TaskCountLimitModifiedEvent(IdentifierId<Long> taskId, TaskCountLimit taskCountLimit) {
        super(TaskImpl.class, TaskCountLimitModifiedEvent.class);
        this.taskId = taskId;
        this.taskCountLimit = taskCountLimit;
    }
}
