package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.account.Account;
import xyz.wochib70.domain.account.AccountRepository;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.credential.Credential;
import xyz.wochib70.domain.credential.CredentialRepository;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemItemPrice;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class ParticipateActivityWithRedeemCmdHandler {


    private final ActivityRepository activityRepository;

    private final RedeemRepository redeemRepository;

    private final AccountRepository accountRepository;

    private final CredentialRepository credentialRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ParticipateActivityWithRedeemCmd cmd) {
        Activity activity = activityRepository.queryActivityByIdOrThrow(cmd.activityId());
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemId());

        //如果配置了凭证限制
        if (activity.useCredentialLimit()) {
            Credential credential = credentialRepository.queryCredentialByUsageCodeOrThrow(cmd.credentialUsageCode());
            credential.participate(cmd.userId());
            credentialRepository.save(credential);
            credential.getEvents().forEach(eventPublisher::publishEvent);
        }
        //兑换
        activity.participate(cmd.userId());
        redeem.redeem(cmd.redeemItemId(), cmd.count(), cmd.userId());
        //扣费
        RedeemItemPrice redeemItemPrice = redeem.getRedeemItemPriceOrThrow(cmd.redeemItemId());
        Account account = accountRepository.queryAccountByCurrencyIdAndUserId(redeemItemPrice.currencyId(), cmd.userId());
        account.decreaseBalance(redeemItemPrice.price() * cmd.count());

        activityRepository.update(activity);
        redeemRepository.update(redeem);

        activity.getEvents().forEach(eventPublisher::publishEvent);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }


}
