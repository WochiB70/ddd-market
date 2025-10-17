package xyz.wochib70.domain.usertask;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskRepository;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserTaskFactory {


    private final UserTaskIdGenerator userTaskIdGenerator;

    private final TaskRepository taskRepository;

    private final ActivityRepository activityRepository;

    public UserTask create(IdentifierId<Long> taskId, UserId userId) {
        ParameterUtil.requireNonNull(taskId, "任务Id不能为null");
        ParameterUtil.requireNonNull(userId, "用户Id不能为null");
        Task task = taskRepository.queryTaskByIdOrThrow(taskId);
        CompleteEvent completeEvent = task.getCompleteEvent();
        final ReceivedTaskExpireTime expireTime = task.getReceivedTaskExpireTime();
        var userTask = new UserTaskImpl(userTaskIdGenerator.nextUserTaskId());
        userTask.setTaskId(taskId);
        userTask.setUserId(userId);
        LocalDateTime userTaskExpireTime = activityRepository.queryActivityById(task.getActivityId())
                .map(expireTime::calculate)
                .orElseThrow();
        userTask.setExpireTime(userTaskExpireTime);
        return userTask;
    }
}
