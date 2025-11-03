package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.Inventory;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class ModifyRedeemItemInventoryTypeCmdHandler {

    private final RedeemRepository redeemRepository;

    private final InventoryRepository inventoryRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyRedeemItemInventoryTypeCmd cmd) {
        redeemRepository.findByIdOrThrow(cmd.redeemId());
        Inventory inventory = inventoryRepository.queryByGoodsIdAndGoodsTypeOrThrow(cmd.redeemItemId(), GoodsType.REDEEM);
        inventory.modifyInventory(cmd.inventoryType());

        inventory.getEvents().forEach(eventPublisher::publishEvent);
    }
}
