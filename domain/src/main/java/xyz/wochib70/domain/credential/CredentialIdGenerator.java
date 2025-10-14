package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface CredentialIdGenerator extends DomainIdGenerator<Long> {

    String generateUsageCode();

    default IdentifierId<Long> nextCredentialId() {
        return nextAggregateId("credential");
    }
}
