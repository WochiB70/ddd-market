package xyz.wochib70.domain.inventory.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.inventory.events.InventoryDecreasedEvent;

@RequiredArgsConstructor
@Component
public class InventoryDecreasedPolicy {

    private final InventoryRepository inventoryRepository;

    @EventListener
    public void handleInventoryDecreasedEvent(InventoryDecreasedEvent event) {
        inventoryRepository.removeInventoryRecord(
                event.getInventoryId(),
                event.getCount()
        );
    }


}
