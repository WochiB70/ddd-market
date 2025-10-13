package xyz.wochib70.domain.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class TaskFactory {

    private final TaskIdGenerator taskIdGenerator;

    private final ActivityRepository activityRepository;

    public Task create(TaskInfo info,
                       TaskCountLimit taskCountLimit,
                       TaskDuration duration,
                       IdentifierId<Long> activityId,
                       ReceivedTaskExpireTime receivedTaskExpireTime
    ) {
        ParameterUtil.requireNonNull(info, "任务信息不能为空");
        ParameterUtil.requireNonNull(taskCountLimit, "任务限制不能为空");
        ParameterUtil.requireNonNull(duration, "任务时间不能为空");
        ParameterUtil.requireNonNull(receivedTaskExpireTime, "任务领取时间不能为空");

        TaskImpl task = new TaskImpl(taskIdGenerator.nextAggregateId());
        task.setDuration(duration);
        task.setInfo(info);
        task.setTaskCountLimit(taskCountLimit);
        if (Objects.nonNull(activityId)) {
            Activity activity = activityRepository.queryActivityByIdOrThrow(activityId);
            ActivityDuration activityDuration = activity.getDuration();
            TaskDuration taskDuration = new TaskDuration.Builder(activityDuration)
                    .startTime(duration.getStartTime())
                    .expiredTime(duration.getExpiredTime())
                    .build();
            task.setDuration(taskDuration);
            task.setActivityId(activityId);
        }
        task.setStatus(TaskStatus.ENABLE);
        task.setReceivedTaskExpireTime(receivedTaskExpireTime);
        task.create();
        return task;
    }

}
