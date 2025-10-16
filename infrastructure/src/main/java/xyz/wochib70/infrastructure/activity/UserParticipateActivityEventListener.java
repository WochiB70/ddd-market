package xyz.wochib70.infrastructure.activity;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.activity.events.ActivityParticipatedEvent;

@AllArgsConstructor
@Component
public class UserParticipateActivityEventListener {

    private final UserParticipateActivityRecordDao userParticipateActivityRecordDao;

    @EventListener
    public void handle(ActivityParticipatedEvent event) {
        UserParticipateActivityRecordEntity entity = new UserParticipateActivityRecordEntity();
        entity.setId(event.getActivityId().getId());
        entity.setActivityId(event.getActivityId().getId());
        entity.setParticipateTime(event.createTime());
        entity.setUserId(event.getUserId().getId());
        userParticipateActivityRecordDao.save(entity);
    }

}
