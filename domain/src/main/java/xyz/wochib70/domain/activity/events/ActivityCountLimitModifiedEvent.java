package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityCountLimit;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityCountLimitModifiedEvent extends AbstractAggregateEvent<Long> {


    private final IdentifierId<Long> activityId;

    private final ActivityCountLimit countLimit;

    public ActivityCountLimitModifiedEvent(
            IdentifierId<Long> activityId,
            ActivityCountLimit countLimit
    ) {
        super(ActivityImpl.class, ActivityCountLimitModifiedEvent.class);
        this.activityId = activityId;
        this.countLimit = countLimit;
    }
}
