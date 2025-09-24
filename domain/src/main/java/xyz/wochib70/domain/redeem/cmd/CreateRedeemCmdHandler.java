package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemFactory;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class CreateRedeemCmdHandler {

    private final RedeemFactory redeemFactory;

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(CreateRedeemCmd cmd) {
        Redeem redeem = redeemFactory.createRedeem(cmd.activityId(), cmd.name());
        redeemRepository.save(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }
}
