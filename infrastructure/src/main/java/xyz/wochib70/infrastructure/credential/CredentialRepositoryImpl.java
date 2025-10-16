package xyz.wochib70.infrastructure.credential;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.*;

@AllArgsConstructor
@Component
public class CredentialRepositoryImpl implements CredentialRepository {

    private final CredentialDao credentialDao;

    @Override
    public Credential findByIdOrThrow(IdentifierId<Long> credentialId) {
        return credentialDao.queryById(credentialId.getId())
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchCredentialException("凭证不存在"));
    }

    @Override
    public Credential findByUsageCodeOrThrow(String usageCode) {
        return credentialDao.queryByUsageCode(usageCode)
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchCredentialException("凭证不存在"));
    }

    @Override
    public void save(Credential credential) {
        CredentialEntity entity = toEntity(credential);
        credentialDao.save(entity);
    }

    @Override
    public void update(Credential credential) {
        CredentialEntity entity = toEntity(credential);
        credentialDao.save(entity);
    }

    @Override
    public Credential queryCredentialByUsageCodeOrThrow(String usageCode) {
        return credentialDao.queryByUsageCode(usageCode)
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchCredentialException("凭证不存在"));
    }

    private CredentialEntity toEntity(Credential credential) {
        CredentialImpl impl = (CredentialImpl) credential;
        CredentialEntity entity = new CredentialEntity();
        entity.setId(impl.getCredentialId().getId());
        entity.setUsageCode(impl.getUsageCode());
        entity.setStartTime(impl.getDuration().startTime());
        entity.setExpiredTime(impl.getDuration().expiredTime());
        entity.setStatus(impl.getStatus());
        entity.setUserId(impl.getUser().getId());
        entity.setUnusedCount(impl.getUnusedCount());
        entity.setUnusedCount(impl.getUnusedCount());
        return entity;
    }

    private Credential toDomain(CredentialEntity entity) {
        CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(entity.getId()));
        credential.setUsageCode(entity.getUsageCode());
        credential.setDuration(new CredentialDuration(entity.getStartTime(), entity.getExpiredTime()));
        credential.setStatus(entity.getStatus());
        credential.setUser(new UserId(entity.getUserId()));
        credential.setUnusedCount(entity.getUnusedCount());
        return credential;
    }
}
