package xyz.wochib70.infrastructure.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.InventoryType;

@Getter
@Setter
@Table(name = "inventory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"goods_id", "type"})
})
@Entity
public class InventoryEntity {

    @Id
    private Long id;

    @Column(name = "goods_id", nullable = false)
    private Long goodsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GoodsType type;

    private Integer count;

    @Enumerated(EnumType.STRING)
    private InventoryType inventoryType;
}
