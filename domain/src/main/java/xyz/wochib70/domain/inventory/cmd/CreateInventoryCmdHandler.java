package xyz.wochib70.domain.inventory.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.inventory.InventoryFactory;
import xyz.wochib70.domain.inventory.InventoryRepository;

@RequiredArgsConstructor
@Component
public class CreateInventoryCmdHandler {

    private final InventoryRepository inventoryRepository;

    private final InventoryFactory inventoryFactory;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(CreateInventoryCmd cmd) {
        var inventory = inventoryFactory.createInventory(
                cmd.goodsId(),
                cmd.goodsType(),
                cmd.inventoryType(),
                cmd.quantity()
        );
        inventoryRepository.save(inventory);
        inventory.getEvents().forEach(eventPublisher::publishEvent);
    }
}
