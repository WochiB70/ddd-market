package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.*;

@Getter
public class ActivityCreatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    private final ActivityInfo info;

    private final ActivityDuration duration;

    private final CountLimit countLimit;

    private final Boolean credentialLimit;

    private final ActivityAwardType awardType;

    public ActivityCreatedEvent(
            IdentifierId<Long> activityId,
            ActivityInfo info,
            ActivityDuration duration,
            CountLimit countLimit,
            Boolean credentialLimit,
            ActivityAwardType awardType
    ) {
        super(ActivityImpl.class, ActivityCreatedEvent.class);
        this.activityId = activityId;
        this.info = info;
        this.duration = duration;
        this.countLimit = countLimit;
        this.credentialLimit = credentialLimit;
        this.awardType = awardType;
    }
}
