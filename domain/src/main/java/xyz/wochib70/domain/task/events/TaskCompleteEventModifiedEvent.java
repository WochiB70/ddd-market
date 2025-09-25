package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskCompleteEventModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final CompleteEvent completeEvent;

    public TaskCompleteEventModifiedEvent(IdentifierId<Long> taskId, CompleteEvent completeEvent) {
        super(TaskImpl.class, TaskCompleteEventModifiedEvent.class);
        this.taskId = taskId;
        this.completeEvent = completeEvent;
    }
}
