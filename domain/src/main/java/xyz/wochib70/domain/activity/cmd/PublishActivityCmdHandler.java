package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class PublishActivityCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(PublishActivityCmd cmd) {
        var activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.publish();
        activityRepository.update(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
    }
}
