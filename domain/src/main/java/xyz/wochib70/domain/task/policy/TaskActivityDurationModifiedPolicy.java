package xyz.wochib70.domain.task.policy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.events.ActivityDurationModifiedEvent;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskDuration;
import xyz.wochib70.domain.task.TaskRepository;
import xyz.wochib70.domain.utils.DurationUtil;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Component
public class TaskActivityDurationModifiedPolicy {

    private final TaskRepository taskRepository;

    @EventListener
    public void handle(ActivityDurationModifiedEvent event) {
        log.info("活动[{}]持续时间段被修改，现在需要修改其绑定任务的持续时间:", event.getActivityId());
        List<Task> tasks = taskRepository.queryTaskByActivityId(event.getActivityId());
        ActivityDuration duration = event.getDuration();
        for (Task task : tasks) {
            log.info("修改任务:[{}]的持续时间段", task.getTaskId());
            TaskDuration taskDuration = task.getDuration();
            LocalDateTime newStartTime = DurationUtil.max(taskDuration.getStartTime(), duration.startTime());
            LocalDateTime newExpiredTime = DurationUtil.min(taskDuration.getExpiredTime(), duration.endTime());
            taskDuration = new TaskDuration.Builder(duration)
                    .startTime(newStartTime)
                    .expiredTime(newExpiredTime)
                    .build();
            task.modifyTaskDuration(taskDuration);
            taskRepository.update(task);
            log.info("任务:[{}]的持续时间段修改完成", task.getTaskId());
        }
        log.info("活动[{}]持续时间段被修改，其绑定的任务的持续时间已完成修改:", event.getActivityId());
    }

}
