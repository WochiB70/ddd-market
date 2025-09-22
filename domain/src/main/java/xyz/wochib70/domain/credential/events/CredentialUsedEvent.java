package xyz.wochib70.domain.credential.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.CredentialImpl;

@Getter
public class CredentialUsedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> credentialId;

    private final String usageCode;

    private final UserId userId;

    public CredentialUsedEvent(
            IdentifierId<Long> credentialId,
            String usageCode,
            UserId userId
    ) {
        super(CredentialImpl.class, CredentialUsedEvent.class);
        this.credentialId = credentialId;
        this.usageCode = usageCode;
        this.userId = userId;
    }
}
