package xyz.wochib70.domain.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.UserId;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CredentialFactory {

    private final CredentialIdGenerator credentialIdGenerator;

    public Credential create(
            CredentialDuration duration,
            Integer unusedCount,
            UserId user
    ) {
        var credentialId = credentialIdGenerator.nextAggregateId();
        var credential = new CredentialImpl(credentialId);
        credential.setDuration(duration);
        credential.setStatus(CredentialStatus.VALID);
        credential.setUser(user);
        credential.setUnusedCount(Objects.isNull(unusedCount) ? 1 : unusedCount);
        credential.setUsageCode(credentialIdGenerator.generateUsageCode());
        return credential;
    }
}
