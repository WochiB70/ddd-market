package xyz.wochib70.domain.usertask.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.usertask.UserTaskImpl;

@Getter
public class UserTaskCompletedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> userTaskId;

    private final IdentifierId<Long> taskId;

    private final UserId userId;

    public UserTaskCompletedEvent(
            IdentifierId<Long> userTaskId,
            IdentifierId<Long> taskId,
            UserId userId
    ) {
        super(UserTaskImpl.class, UserTaskCompletedEvent.class);
        this.userTaskId = userTaskId;
        this.taskId = taskId;
        this.userId = userId;
    }
}
