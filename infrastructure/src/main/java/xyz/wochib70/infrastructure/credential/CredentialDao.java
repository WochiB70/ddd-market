package xyz.wochib70.infrastructure.credential;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CredentialDao extends JpaRepository<CredentialEntity, Long> {

    Optional<CredentialEntity> queryById(Long id);

    Optional<CredentialEntity> queryByUsageCode(String usageCode);

    void updateById(CredentialEntity entity);
}
