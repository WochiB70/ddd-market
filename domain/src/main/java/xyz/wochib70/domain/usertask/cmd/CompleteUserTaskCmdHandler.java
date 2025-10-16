package xyz.wochib70.domain.usertask.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.usertask.UserTask;
import xyz.wochib70.domain.usertask.UserTaskRepository;

@RequiredArgsConstructor
@Service
public class CompleteUserTaskCmdHandler {

    private final UserTaskRepository userTaskRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(CompleteUserTaskCmd cmd) {
        UserTask userTask = userTaskRepository.queryUncompletedUserTaskByTaskIdAndUserIdOrThrow(
                cmd.userTaskId(),
                cmd.userId()
        );
        userTask.complete();
        userTaskRepository.save(userTask);
        userTask.getEvents().forEach(eventPublisher::publishEvent);
    }
}
