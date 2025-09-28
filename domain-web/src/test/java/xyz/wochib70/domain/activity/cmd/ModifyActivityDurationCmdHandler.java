package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class ModifyActivityDurationCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyActivityDurationCmd cmd) {
        Activity activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.modifyDuration(cmd.duration());
        activityRepository.update(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
    }
}
