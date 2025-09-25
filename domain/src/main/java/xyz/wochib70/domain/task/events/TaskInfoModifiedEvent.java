package xyz.wochib70.domain.task.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskImpl;
import xyz.wochib70.domain.task.TaskInfo;

@Getter
public class TaskInfoModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> taskId;

    private final TaskInfo info;

    public TaskInfoModifiedEvent(IdentifierId<Long> taskId, TaskInfo info) {
        super(TaskImpl.class, TaskInfoModifiedEvent.class);
        this.taskId = taskId;
        this.info = info;
    }
}
