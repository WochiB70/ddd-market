package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityClosedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    public ActivityClosedEvent(IdentifierId<Long> activityId) {
        super(ActivityImpl.class, ActivityClosedEvent.class);
        this.activityId = activityId;
    }
}
