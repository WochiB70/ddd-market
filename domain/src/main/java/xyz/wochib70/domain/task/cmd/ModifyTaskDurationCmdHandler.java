package xyz.wochib70.domain.task.cmd;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskDuration;
import xyz.wochib70.domain.task.TaskRepository;

import java.util.Objects;

@AllArgsConstructor
@Component
public class ModifyTaskDurationCmdHandler {

    private final TaskRepository taskRepository;

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyTaskDurationCmd cmd) {
        Task task = taskRepository.queryTaskByIdOrThrow(cmd.taskId());
        TaskDuration taskDuration = Objects.isNull(task.getActivityId()) ?
                new TaskDuration.Builder(null)
                        .startTime(cmd.duration().getStartTime())
                        .expiredTime(cmd.duration().getExpiredTime())
                        .build() :
                calculateDuration(task.getActivityId(), cmd);
        task.modifyTaskDuration(taskDuration);
        taskRepository.update(task);
        task.getEvents().forEach(eventPublisher::publishEvent);
    }


    private TaskDuration calculateDuration(IdentifierId<Long> activityId, ModifyTaskDurationCmd cmd) {
        Activity activity = activityRepository.queryActivityByIdOrThrow(activityId);
        ActivityDuration activityDuration = activity.getDuration();
        return new TaskDuration.Builder(activityDuration)
                .startTime(cmd.duration().getStartTime())
                .expiredTime(cmd.duration().getExpiredTime())
                .build();
    }
}
