package xyz.wochib70.infrastructure.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRecordDao extends JpaRepository<InventoryRecordEntity, Long> {


    @Lock(jakarta.persistence.LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    List<InventoryRecordEntity> findByInventoryIdAndStatus(Long inventoryId, InventoryRecordStatus status);
}