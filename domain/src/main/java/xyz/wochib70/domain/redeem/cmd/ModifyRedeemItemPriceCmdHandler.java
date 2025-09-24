package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class ModifyRedeemItemPriceCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;


    public void handle(ModifyRedeemItemPriceCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemId());
        redeem.modifyRedeemItemPrice(cmd.redeemItemId(), cmd.price());
        redeemRepository.update(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }
}
