package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityStartedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;


    public ActivityStartedEvent(IdentifierId<Long> activityId) {
        super(ActivityImpl.class, ActivityStartedEvent.class);
        this.activityId = activityId;
    }
}
