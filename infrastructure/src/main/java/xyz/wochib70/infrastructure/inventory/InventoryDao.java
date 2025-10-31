package xyz.wochib70.infrastructure.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.wochib70.domain.inventory.GoodsType;

import java.util.Optional;

@Repository
public interface InventoryDao extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByGoodsIdAndType(Long goodsId, GoodsType type);
}