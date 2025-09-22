package xyz.wochib70.domain.credential.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.CredentialImpl;

@Getter
public class CredentialInvalidatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> credentialId;

    public CredentialInvalidatedEvent(IdentifierId<Long> credentialId) {
        super(CredentialImpl.class, CredentialInvalidatedEvent.class);
        this.credentialId = credentialId;
    }
}
