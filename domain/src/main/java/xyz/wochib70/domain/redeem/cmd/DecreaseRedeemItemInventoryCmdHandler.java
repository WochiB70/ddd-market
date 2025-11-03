package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.draw.DrawPoolRepository;
import xyz.wochib70.domain.draw.cmd.DecreaseDrawItemInventoryCmd;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Component
public class DecreaseRedeemItemInventoryCmdHandler {


    private final RedeemRepository redeemRepository;

    private final InventoryRepository inventoryRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(DecreaseRedeemItemInventoryCmd cmd) {
        redeemRepository.findByIdOrThrow(cmd.redeemId());
        var inventory = inventoryRepository.queryByGoodsIdAndGoodsTypeOrThrow(cmd.redeemItemId(), GoodsType.DRAW);
        inventory.decreaseInventory(cmd.decrease());
        inventoryRepository.update(inventory);
        inventory.getEvents().forEach(eventPublisher::publishEvent);
    }

}
