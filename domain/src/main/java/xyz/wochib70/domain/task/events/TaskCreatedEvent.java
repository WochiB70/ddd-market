package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskImpl;

@Getter
public class TaskCreatedEvent extends AbstractAggregateEvent<Long> {

    private final Task task;

    public TaskCreatedEvent(Task task) {
        super(TaskImpl.class, TaskCreatedEvent.class);
        this.task = task;
    }
}
