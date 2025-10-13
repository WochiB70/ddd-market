package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class ModifyActivityAwardTypeCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyActivityAwardTypeCmd cmd) {
        var activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.modifyAwardType(cmd.awardType());
        activityRepository.update(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
    }

}
