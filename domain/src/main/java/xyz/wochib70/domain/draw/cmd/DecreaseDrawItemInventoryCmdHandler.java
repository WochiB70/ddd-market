package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.draw.DrawPoolRepository;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.InventoryRepository;

@RequiredArgsConstructor
@Component
public class DecreaseDrawItemInventoryCmdHandler {


    private final DrawPoolRepository drawPoolRepository;

    private final InventoryRepository inventoryRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(DecreaseDrawItemInventoryCmd cmd) {
        drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        var inventory = inventoryRepository.queryByGoodsIdAndGoodsTypeOrThrow(cmd.drawItemId(), GoodsType.DRAW);
        inventory.decreaseInventory(cmd.decrease());
        inventoryRepository.update(inventory);
        inventory.getEvents().forEach(eventPublisher::publishEvent);
    }

}
