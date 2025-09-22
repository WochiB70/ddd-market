package xyz.wochib70.domain.credential.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.CredentialImpl;

@Getter
public class CredentialValidatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> credentialId;

    public CredentialValidatedEvent(IdentifierId<Long> credentialId) {
        super(CredentialImpl.class, CredentialValidatedEvent.class);
        this.credentialId = credentialId;
    }
}
