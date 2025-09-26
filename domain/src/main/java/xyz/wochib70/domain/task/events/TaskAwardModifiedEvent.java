package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskAward;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskAwardModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final TaskAward taskAward;

    public TaskAwardModifiedEvent(IdentifierId<Long> taskId, TaskAward taskAward) {
        super(TaskImpl.class, TaskAwardModifiedEvent.class);
        this.taskId = taskId;
        this.taskAward = taskAward;
    }
}
