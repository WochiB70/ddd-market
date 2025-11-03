package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.draw.DrawPoolRepository;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.Inventory;
import xyz.wochib70.domain.inventory.InventoryRepository;

@RequiredArgsConstructor
@Service
public class ModifyDrawItemInventoryTypeCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final InventoryRepository inventoryRepository;

    public void handle(ModifyDrawItemInventoryTypeCmd cmd) {
        drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        Inventory inventory = inventoryRepository.queryByGoodsIdAndGoodsTypeOrThrow(cmd.awardId(), GoodsType.DRAW);
        inventory.modifyInventory(cmd.inventoryType());
        inventoryRepository.update(inventory);
        inventory.getEvents().forEach(eventPublisher::publishEvent);
    }
}
