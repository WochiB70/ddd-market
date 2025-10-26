package xyz.wochib70.domain.task.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskFactory;
import xyz.wochib70.domain.task.TaskRepository;

@RequiredArgsConstructor
@Service
public class CreateTaskCmdHandler {

    private final TaskFactory taskFactory;

    private final TaskRepository taskRepository;

    private final ApplicationEventPublisher eventPublisher;

    public IdentifierId<Long> handle(CreateTaskCmd cmd) {
        var task = taskFactory.create(
                cmd.info(),
                cmd.taskCountLimit(),
                cmd.duration(),
                cmd.activityId(),
                cmd.receivedTaskExpireTime(),
                cmd.completeEvent(),
                cmd.award()
        );
        taskRepository.save(task);
        task.getEvents().forEach(eventPublisher::publishEvent);
        return task.getTaskId();
    }
}
