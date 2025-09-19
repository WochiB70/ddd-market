package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityImpl;
import xyz.wochib70.domain.activity.ActivityInfo;

@Getter
public class ActivityInfoModifiedEvent  extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    private final ActivityInfo info;

    public ActivityInfoModifiedEvent(
            IdentifierId<Long> activityId,
            ActivityInfo info
    ) {
        super(ActivityImpl.class, ActivityInfoModifiedEvent.class);
        this.activityId = activityId;
        this.info = info;
    }
}
