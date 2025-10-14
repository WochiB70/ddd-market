package xyz.wochib70.domain.credential.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialDuration;
import xyz.wochib70.domain.credential.CredentialFactory;
import xyz.wochib70.domain.credential.CredentialRepository;
import xyz.wochib70.domain.task.Task;
import xyz.wochib70.domain.task.TaskAward;
import xyz.wochib70.domain.task.TaskAwardType;
import xyz.wochib70.domain.task.TaskRepository;
import xyz.wochib70.domain.usertask.events.UserTaskCompletedEvent;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class CredentialUserTaskCompletedPolicy {

    private final TaskRepository taskRepository;

    private final CredentialFactory factory;

    private final ApplicationEventPublisher eventPublisher;

    private final ActivityRepository activityRepository;

    private final CredentialRepository credentialRepository;

    @EventListener
    public void handle(UserTaskCompletedEvent event) {
        Task task = taskRepository.queryTaskByIdOrThrow(event.getTaskId());
        TaskAward award = task.getTaskAward();
        if (!Objects.equals(award.type(), TaskAwardType.CREDENTIALS)) {
            return;
        }
        Activity activity = activityRepository.queryActivityByIdOrThrow(task.getActivityId());
        log.info("用户{}完成任务{}，为活动{}发放凭证， 数量：{}", event.getUserId(), event.getTaskId(), task.getActivityId(), award.count());
        CredentialDuration duration = new CredentialDuration.Builder(activity.getDuration())
                .startTime(activity.getDuration().startTime())
                .expiredTime(activity.getDuration().endTime())
                .build();
        Credential credential = factory.create(duration, award.count(), event.getUserId());
        credentialRepository.save(credential);
        credential.getEvents().forEach(eventPublisher::publishEvent);
        log.info("用户{}完成任务{}，为活动{}发放凭证成功, 凭证Id；{}", event.getUserId(), event.getTaskId(), task.getActivityId(), credential.getCredentialId());
    }
}
