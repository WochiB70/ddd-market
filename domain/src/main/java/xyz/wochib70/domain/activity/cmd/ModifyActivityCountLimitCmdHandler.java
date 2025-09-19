package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class ModifyActivityCountLimitCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyActivityCountLimitCmd cmd) {
        var activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.modifyCountLimit(cmd.countLimit());
        activityRepository.update(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
    }
}
