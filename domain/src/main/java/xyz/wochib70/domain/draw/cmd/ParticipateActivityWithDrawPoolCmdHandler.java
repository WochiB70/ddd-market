package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.account.Account;
import xyz.wochib70.domain.account.AccountRepository;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialRepository;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolRepository;
import xyz.wochib70.domain.draw.DrawPrice;
import xyz.wochib70.domain.draw.Reward;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.Inventory;
import xyz.wochib70.domain.inventory.InventoryRepository;

@RequiredArgsConstructor
@Service
public class ParticipateActivityWithDrawPoolCmdHandler {

    private final ActivityRepository activityRepository;

    private final DrawPoolRepository drawPoolRepository;

    private final CredentialRepository credentialRepository;

    private final AccountRepository accountRepository;

    private final InventoryRepository inventoryRepository;

    private final ApplicationEventPublisher eventPublisher;

    public Reward handle(ParticipateActivityWithDrawPoolCmd cmd) {
        Activity activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());

        if (activity.useCredentialLimit()) {
            Credential credential = credentialRepository.queryCredentialByUsageCodeOrThrow(cmd.credentialUsageCode());
            credential.participate(cmd.userId());
            credentialRepository.save(credential);
            credential.getEvents().forEach(eventPublisher::publishEvent);
        }
        DrawPool drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());

        DrawPrice drawPrice = drawPool.getDrawPrice();
        Account account = accountRepository.queryAccountByCurrencyIdAndUserId(drawPrice.currencyId(), cmd.userId());
        account.decreaseBalance(drawPrice.price());
        activity.participate(cmd.userId());

        Reward draw = drawPool.draw(cmd.userId());

        Inventory inventory = inventoryRepository.queryByGoodsIdAndGoodsTypeOrThrow(draw.awardId(), GoodsType.DRAW);
        inventory.useInventory(1);
        inventoryRepository.update(inventory);

        drawPoolRepository.update(drawPool);
        activityRepository.update(activity);

        inventory.getEvents().forEach(eventPublisher::publishEvent);
        activity.getEvents().forEach(eventPublisher::publishEvent);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
        return draw;
    }

}
