package xyz.wochib70.domain.usertask.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.task.events.TaskReceivedEvent;
import xyz.wochib70.domain.usertask.UserTask;
import xyz.wochib70.domain.usertask.UserTaskFactory;
import xyz.wochib70.domain.usertask.UserTaskRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaskReceivedEventPolicy {

    private final UserTaskRepository userTaskRepository;


    private final UserTaskFactory userTaskFactory;

    @EventListener
    public void handle(TaskReceivedEvent event) {
        log.info("用户:[{}]任务领取任务:[{}]", event.getUserId(), event.getTaskId());
        UserTask userTask = userTaskFactory.create(event.getTaskId(), event.getUserId());
        userTaskRepository.save(userTask);
    }
}
