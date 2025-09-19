package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityDurationModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    private final ActivityDuration duration;

    public ActivityDurationModifiedEvent(
            IdentifierId<Long> activityId,
            ActivityDuration duration
    ) {
        super(ActivityImpl.class, ActivityDurationModifiedEvent.class);
        this.activityId = activityId;
        this.duration = duration;
    }
}
