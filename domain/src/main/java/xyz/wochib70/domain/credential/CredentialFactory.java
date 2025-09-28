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
        Objects.requireNonNull(duration, "有效期不能为null");
        Objects.requireNonNull(user, "用户Id不能为null");
        Objects.requireNonNull(unusedCount, "使用次数不能为null");
        if (unusedCount < 1) {
            throw new IllegalArgumentException("使用次数不能小于1");
        }
        var credentialId = credentialIdGenerator.nextAggregateId();
        var credential = new CredentialImpl(credentialId);
        credential.setDuration(duration);
        credential.setStatus(CredentialStatus.VALID);
        credential.setUser(user);
        credential.setUnusedCount(unusedCount);
        credential.setUsageCode(credentialIdGenerator.generateUsageCode());
        return credential;
    }
}
