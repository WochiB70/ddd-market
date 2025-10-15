package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityParticipatedEvent extends AbstractAggregateEvent<Long> {

    private final UserId userId;

    private final IdentifierId<Long> activityId;

    public ActivityParticipatedEvent(
            UserId userId,
            IdentifierId<Long> activityId
    ) {
        super(ActivityImpl.class, ActivityParticipatedEvent.class);
        this.userId = userId;
        this.activityId = activityId;
    }
}
