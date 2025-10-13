package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityFactory;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class CreateActivityCmdHandler {


    private final ActivityFactory activityFactory;

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public IdentifierId<Long> handle(CreateActivityCmd cmd) {
        Activity activity = activityFactory.createActivity(
                cmd.info(),
                cmd.duration(),
                cmd.countLimit(),
                cmd.credentialLimit(),
                cmd.awardType()
        );
        activityRepository.save(activity);
        activity.getEvents().forEach(eventPublisher::publishEvent);
        return activity.getActivityId();
    }
}
