package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityAwardTypeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    private final ActivityAwardType awardType;

    public ActivityAwardTypeModifiedEvent(
            IdentifierId<Long> activityId,
            ActivityAwardType awardType
    ) {
        super(ActivityImpl.class, ActivityAwardTypeModifiedEvent.class);
        this.activityId = activityId;
        this.awardType = awardType;
    }
}
