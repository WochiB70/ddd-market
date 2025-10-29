package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskDuration;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskDurationModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final TaskDuration duration;

    public TaskDurationModifiedEvent(
            IdentifierId<Long> taskId,
            TaskDuration duration
    ) {
        super(TaskImpl.class, TaskDurationModifiedEvent.class);
        this.taskId = taskId;
        this.duration = duration;
    }
}
