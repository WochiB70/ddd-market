package xyz.wochib70.infrastructure.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "inventory_record")
@Entity
public class InventoryRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long inventoryId;

    @Enumerated(EnumType.STRING)
    private InventoryRecordStatus status;

    @Version
    private Long version;
}
