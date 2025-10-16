package xyz.wochib70.infrastructure.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> queryAccountEntityByCurrencyIdAndUserId(Long currencyId, Long userId);

}
