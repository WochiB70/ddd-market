package xyz.wochib70.domain.activity.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.activity.ActivityRepository;

@RequiredArgsConstructor
@Service
public class ParticipateActivityCmdHandler {

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ParticipateActivityCmd cmd) {
        var activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        activity.participate(cmd.userId());
        activityRepository.update(activity);
        //TODO 完善兑换池、抽奖池和凭证之后继续完善

        activity.getEvents().forEach(eventPublisher::publishEvent);
    }
}
