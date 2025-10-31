package xyz.wochib70.domain.inventory.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.inventory.InventoryType;
import xyz.wochib70.domain.inventory.events.InventoryModifiedEvent;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryModifiedPolicy {

    private final InventoryRepository inventoryRepository;

    @EventListener
    public void handleInventoryTypeEvent(InventoryModifiedEvent event) {
        if (event.getInventoryType() == InventoryType.INFINITE) {
            log.info("库存类型修改为无限，需要删除掉之前创建并且没有使用掉的record");
            inventoryRepository.removeUnusedInventoryRecord(event.getInventoryId());
        }
    }
}
