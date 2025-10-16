package xyz.wochib70.infrastructure.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final TaskDao taskDao;

    private final TaskAwardDao taskAwardDao;

    private final UserTaskDao userTaskDao;

    @Override
    public Task queryTaskByIdOrThrow(IdentifierId<Long> taskId) {
        TaskAwardEntity awardEntity = taskAwardDao.queryByTaskId(taskId.getId());
        return taskDao.queryTaskById(taskId.getId())
                .map(entity -> toDomain(entity, awardEntity))
                .orElseThrow(() -> new NoSuchTaskException("任务不存在"));
    }

    @Override
    public void save(Task task) {
        TaskEntity entity = toEntity(task);
        TaskAwardEntity awardEntity = toAwardEntity(task.getTaskAward());
        awardEntity.setTaskId(entity.getId());
        taskDao.save(entity);
        taskAwardDao.save(awardEntity);
    }

    @Override
    public void update(Task task) {
        TaskEntity entity = toEntity(task);
        TaskAwardEntity awardEntity = toAwardEntity(task.getTaskAward());
        awardEntity.setTaskId(entity.getId());
        taskDao.save(entity);
        taskAwardDao.save(awardEntity);
    }

    @Override
    public Integer countReceivedTaskInDay(UserId userId, IdentifierId<Long> taskId) {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return userTaskDao.countByUserIdAndTaskIdAndCreateTimeBetween(
                userId.getId(), taskId.getId(), startOfDay, endOfDay);
    }

    @Override
    public Integer countReceivedTaskByUserIdInDuration(UserId userId, IdentifierId<Long> taskId) {
        // 假设TaskDuration包含startTime和expiredTime
        // 此处应该从TaskEntity获取任务的有效期范围
        TaskEntity taskEntity = taskDao.queryTaskById(taskId.getId())
                .orElseThrow(() -> new NoSuchTaskException("任务不存在"));

        return userTaskDao.countByUserIdAndTaskIdAndCreateTimeBetween(
                userId.getId(),
                taskId.getId(),
                taskEntity.getStartTime(),
                taskEntity.getExpiredTime());
    }

    @Override
    public List<Task> queryReceivableTaskByCompleteEvent(CompleteEvent completeEvent) {
        List<TaskEntity> taskEntities = taskDao.findByCompleteEvent(completeEvent);
        return taskEntities.stream()
                .map(entity -> {
                    TaskAwardEntity awardEntity = taskAwardDao.queryByTaskId(entity.getId());
                    return toDomain(entity, awardEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> queryTaskByActivityId(IdentifierId<Long> activityId) {
        List<TaskEntity> taskEntities = taskDao.findByActivityId(activityId.getId());
        return taskEntities.stream()
                .map(entity -> {
                    TaskAwardEntity awardEntity = taskAwardDao.queryByTaskId(entity.getId());
                    return toDomain(entity, awardEntity);
                })
                .collect(Collectors.toList());
    }


    private Task toDomain(TaskEntity entity, TaskAwardEntity awardEntity) {
        TaskImpl task = new TaskImpl(new DefaultIdentifierId<>(entity.getId()));
        task.setActivityId(new DefaultIdentifierId<>(entity.getActivityId()));
        task.setInfo(new TaskInfo(entity.getName(), entity.getDescription()));
        task.setStatus(entity.getStatus());
        task.setDuration(new TaskDuration(entity.getStartTime(), entity.getExpiredTime()));
        task.setCompleteEvent(entity.getCompleteEvent());
        task.setTaskCountLimit(new TaskCountLimit(entity.getCountLimitType(), entity.getCountLimit()));
        task.setReceivedTaskExpireTime(new ReceivedTaskExpireTime(entity.getReceivedTaskExpireTimeType(), entity.getSeconds()));
        task.setTaskAward(new TaskAward(awardEntity.getAwardType(), new DefaultIdentifierId<>(awardEntity.getId()), awardEntity.getAwardCount()));
        return task;
    }

    private TaskEntity toEntity(Task task) {
        TaskImpl impl = (TaskImpl) task;
        TaskEntity entity = new TaskEntity();
        entity.setId(impl.getTaskId().getId());
        entity.setActivityId(impl.getActivityId().getId());
        entity.setName(impl.getInfo().name());
        entity.setDescription(impl.getInfo().description());
        entity.setStatus(impl.getStatus());
        entity.setStartTime(impl.getDuration().getStartTime());
        entity.setExpiredTime(impl.getDuration().getExpiredTime());
        entity.setCompleteEvent(impl.getCompleteEvent());
        entity.setCountLimitType(impl.getTaskCountLimit().type());
        entity.setCountLimit(impl.getTaskCountLimit().count());
        entity.setReceivedTaskExpireTimeType(impl.getReceivedTaskExpireTime().type());
        entity.setSeconds(impl.getReceivedTaskExpireTime().seconds());
        return entity;
    }

    private TaskAwardEntity toAwardEntity(TaskAward taskAward) {
        TaskAwardEntity entity = new TaskAwardEntity();
        entity.setId(taskAward.awardId().getId());
        entity.setAwardType(taskAward.type());
        entity.setAwardCount(taskAward.count());
        return entity;
    }
}