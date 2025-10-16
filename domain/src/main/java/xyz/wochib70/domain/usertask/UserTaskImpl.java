package xyz.wochib70.domain.usertask;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.usertask.events.UserTaskCompletedEvent;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public non-sealed class UserTaskImpl extends AbstractAggregate<Long> implements UserTask {

    private UserId userId;

    private IdentifierId<Long> taskId;

    private LocalDateTime expireTime;

    private UserTaskStatus status;

    public UserTaskImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getUserTaskId() {
        return identifierId();
    }

    @Override
    public void complete() {
        if (LocalDateTime.now().isAfter(expireTime)) {
            throw new UserTaskExpiredException("任务已过期");
        }
        if (!Objects.equals(status, UserTaskStatus.COMPLETED)) {
            status = UserTaskStatus.COMPLETED;
            publishEvent(new UserTaskCompletedEvent(
                    getUserTaskId(),
                    getTaskId(),
                    getUserId()
            ));
        }
    }
}
