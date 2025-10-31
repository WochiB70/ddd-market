package xyz.wochib70.domain.inventory.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.InventoryImpl;
import xyz.wochib70.domain.inventory.InventoryType;

@Getter
public class InventoryUsedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> inventoryId;

    private final InventoryType type;

    private final Integer count;


    public InventoryUsedEvent(
            IdentifierId<Long> inventoryId,
            InventoryType type,
            Integer count
    ) {
        super(InventoryImpl.class, InventoryUsedEvent.class);
        this.inventoryId = inventoryId;
        this.type = type;
        this.count = count;
    }
}
