package xyz.wochib70.domain.credential.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.CredentialImpl;

@Getter
public class CredentialUnsedCountModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> credentialId;

    private final int unusedCount;

    public CredentialUnsedCountModifiedEvent(
            IdentifierId<Long> credentialId,
            int unusedCount
    ) {
        super(CredentialImpl.class, CredentialUnsedCountModifiedEvent.class);
        this.credentialId = credentialId;
        this.unusedCount = unusedCount;
    }
}
