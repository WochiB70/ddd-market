package xyz.wochib70.domain.inventory.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.InventoryImpl;
import xyz.wochib70.domain.inventory.InventoryType;

@Getter
public class InventoryModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> inventoryId;

    private final InventoryType inventoryType;

    private final Integer count;

    public InventoryModifiedEvent(
            IdentifierId<Long> inventoryId,
            InventoryType inventoryType,
            Integer count
    ) {
        super(InventoryImpl.class, InventoryModifiedEvent.class);
        this.inventoryId = inventoryId;
        this.inventoryType = inventoryType;
        this.count = count;
    }
}
