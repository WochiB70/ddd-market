package xyz.wochib70.domain.inventory.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.inventory.InventoryType;
import xyz.wochib70.domain.inventory.events.InventoryCreatedEvent;
import xyz.wochib70.domain.inventory.events.InventoryIncreasedEvent;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryCreatedAndIncreasedPolicy {

    private final InventoryRepository inventoryRepository;

    @EventListener
    public void handleInventoryCreatedEvent(InventoryCreatedEvent event) {
        if (event.getInventory().getType() == InventoryType.INFINITE){
            log.info("库存类型为无限库存，不需要生成库存记录");
            return;
        }
        inventoryRepository.generateInventoryRecord(
                event.getInventory().getInventoryId(),
                event.getInventory().getCount()
        );
    }


    @EventListener
    public void handleInventoryIncreasedEvent(InventoryIncreasedEvent event) {
        inventoryRepository.generateInventoryRecord(
                event.getInventoryId(),
                event.getCount()
        );
    }
}
