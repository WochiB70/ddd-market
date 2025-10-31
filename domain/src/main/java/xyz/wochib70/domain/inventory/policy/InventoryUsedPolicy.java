package xyz.wochib70.domain.inventory.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.inventory.InventoryType;
import xyz.wochib70.domain.inventory.events.InventoryUsedEvent;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryUsedPolicy {

    private final InventoryRepository inventoryRepository;

    @EventListener
    public void handleInventoryUsedEvent(InventoryUsedEvent event) {
        log.info("使用扣减库存:[{}]", event.getInventoryId());
        if (event.getType() == InventoryType.INFINITE) {
            log.info("库存类型为无限库存，不需要扣减库存");
        }
        inventoryRepository.useInventoryRecord(
                event.getInventoryId(),
                event.getCount()
        );
    }
}
