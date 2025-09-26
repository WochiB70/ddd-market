package xyz.wochib70.domain.usertask.cmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskCannotClaimedException;
import xyz.wochib70.domain.task.TaskCountLimitException;
import xyz.wochib70.domain.task.TaskRepository;
import xyz.wochib70.domain.usertask.UserTaskRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReceiveAndCompleteTaskByEventCmdHandler {

    private final TaskRepository taskRepository;

    private final UserTaskRepository userTaskRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ReceiveAndCompleteTaskByEventCmd cmd) {
        List<Task> tasks = taskRepository.queryReceivableTaskByCompleteEvent(cmd.completeEvent());
        for (Task task : tasks) {
            try {
                task.receive(cmd.userId());
                task.getEvents().forEach(eventPublisher::publishEvent);
                userTaskRepository.queryUncompletedUserTaskByUserIdAndTaskId(
                        cmd.userId(),
                        task.getTaskId()
                ).ifPresent(userTask -> {
                    userTask.complete();
                    userTaskRepository.save(userTask);
                    userTask.getEvents().forEach(eventPublisher::publishEvent);
                });
            } catch (TaskCountLimitException e) {
                log.warn("用户:[{}]任:[{}]务已超过限制", cmd.userId(), task.getTaskId());
            } catch (TaskCannotClaimedException e) {
                log.warn("用户:[{}]任务:[{}]不能被领取", cmd.userId(), task.getTaskId());
            }
        }
    }
}
