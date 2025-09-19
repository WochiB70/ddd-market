package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class CloseActivityCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(CloseActivityCmd cmd) {
        var activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.close();
        activityRepository.update(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
    }
}
