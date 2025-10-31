package xyz.wochib70.domain.inventory.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.inventory.InventoryImpl;

@Getter
public class InventoryCreatedEvent extends AbstractAggregateEvent<Long> {

    private final InventoryImpl inventory;


    public InventoryCreatedEvent(InventoryImpl inventory) {
        super(InventoryImpl.class, InventoryCreatedEvent.class);
        this.inventory = inventory;
    }
}
