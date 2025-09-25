package xyz.wochib70.domain.task.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskRepository;

@RequiredArgsConstructor
@Service
public class ModifyTaskCountLimitCmdHandler {

    private final TaskRepository taskRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyTaskCountLimitCmd cmd) {
        Task task = taskRepository.queryTaskByIdOrThrow(cmd.taskId());
        task.modifyCountLimit(cmd.countLimit());
        task.getEvents().forEach(eventPublisher::publishEvent);
    }
}
