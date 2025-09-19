package xyz.wochib70.domain.activity.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityImpl;

@Getter
public class ActivityCredentialLimitModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> activityId;

    private final Boolean credentialLimit;

    public ActivityCredentialLimitModifiedEvent(
            IdentifierId<Long> activityId,
            Boolean credentialLimit
    ) {
        super(ActivityImpl.class, ActivityCredentialLimitModifiedEvent.class);
        this.activityId = activityId;
        this.credentialLimit = credentialLimit;
    }
}
