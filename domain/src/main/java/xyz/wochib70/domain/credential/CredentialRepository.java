package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.IdentifierId;

public interface CredentialRepository {


    Credential findByIdOrThrow(IdentifierId<Long> credentialId);

    Credential findByUsageCodeOrThrow(String usageCode);

    void save(Credential credential);
}
