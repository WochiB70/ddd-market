package xyz.wochib70.domain.task.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.activity.events.ActivityClosedEvent;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ActivityClosedPolicy {

    private final TaskRepository taskRepository;

    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handle(ActivityClosedEvent event) {
        log.info("活动:[{}]已关闭, 将其关联的任务改为UNABLE", event.getActivityId());
        List<Task> tasks = taskRepository.queryTaskByActivityId(event.getActivityId());
        for (Task task : tasks) {
            task.disable();
            taskRepository.save(task);
            task.getEvents().forEach(eventPublisher::publishEvent);
            log.info("任务:[{}]已改为UNABLE", task.getTaskId());
        }
        log.info("活动:[{}]已关闭, 关联的任务已改为UNABLE", event.getActivityId());
    }
}
