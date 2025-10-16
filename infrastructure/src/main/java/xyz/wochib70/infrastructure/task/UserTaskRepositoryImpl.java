package xyz.wochib70.infrastructure.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.usertask.*;

import java.util.Optional;

@AllArgsConstructor
@Component
public class UserTaskRepositoryImpl implements UserTaskRepository {

    private final UserTaskDao userTaskDao;

    @Override
    public UserTask queryUncompletedUserTaskByTaskIdAndUserIdOrThrow(IdentifierId<Long> userTaskId, UserId userId) {
        return userTaskDao.findUncompletedByIdAndUserId(userTaskId.getId(), userId.getId(), UserTaskStatus.COMPLETED)
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchUserTaskException("用户任务不存在"));
    }

    @Override
    public void save(UserTask userTask) {
        UserTaskEntity entity = toEntity(userTask);
        userTaskDao.save(entity);
    }

    @Override
    public Optional<UserTask> queryUncompletedUserTaskByUserIdAndTaskId(UserId userId, IdentifierId<Long> taskId) {
        return userTaskDao.findUncompletedByUserIdAndTaskId(userId.getId(), taskId.getId(), UserTaskStatus.COMPLETED)
                .map(this::toDomain);
    }

    @Override
    public UserTask queryUserTaskByIdOrThrow(IdentifierId<Long> userTaskId) {
        return userTaskDao.findById(userTaskId.getId())
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchUserTaskException("用户任务不存在"));
    }

    private UserTask toDomain(UserTaskEntity entity) {
        UserTaskImpl userTask = new UserTaskImpl(new DefaultIdentifierId<>(entity.getId()));
        userTask.setUserId(new UserId(entity.getUserId()));
        userTask.setTaskId(new DefaultIdentifierId<>(entity.getTaskId()));
        userTask.setExpireTime(entity.getExpireTime());
        userTask.setStatus(entity.getStatus());
        return userTask;
    }

    private UserTaskEntity toEntity(UserTask userTask) {
        UserTaskImpl impl = (UserTaskImpl) userTask;
        UserTaskEntity entity = new UserTaskEntity();
        entity.setId(impl.getUserTaskId().getId());
        entity.setUserId(impl.getUserId().getId());
        entity.setTaskId(impl.getTaskId().getId());
        entity.setExpireTime(impl.getExpireTime());
        entity.setStatus(impl.getStatus());
        return entity;
    }
}