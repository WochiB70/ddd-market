package xyz.wochib70.domain.usertask.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.usertask.UserTaskImpl;

@Getter
public class UserTaskCompletedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> userTaskId;

    public UserTaskCompletedEvent(IdentifierId<Long> userTaskId) {
        super(UserTaskImpl.class, UserTaskCompletedEvent.class);
        this.userTaskId = userTaskId;
    }
}
