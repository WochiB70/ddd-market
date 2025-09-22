package xyz.wochib70.domain.credential.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.CredentialDuration;
import xyz.wochib70.domain.credential.CredentialImpl;

@Getter
public class CredentialDurationModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> credentialId;

    private final CredentialDuration duration;

    public CredentialDurationModifiedEvent(
            IdentifierId<Long> credentialId,
            CredentialDuration duration
    ) {
        super(CredentialImpl.class, CredentialDurationModifiedEvent.class);
        this.credentialId = credentialId;
        this.duration = duration;
    }
}
