package xyz.wochib70.infrastructure.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyDao extends JpaRepository<CurrencyEntity, Long> {


    Optional<CurrencyEntity> queryById(Long id);
}
