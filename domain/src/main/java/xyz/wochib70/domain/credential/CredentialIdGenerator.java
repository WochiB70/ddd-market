package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.DomainIdGenerator;

public interface CredentialIdGenerator extends DomainIdGenerator<Long> {

    String generateUsageCode();
}
