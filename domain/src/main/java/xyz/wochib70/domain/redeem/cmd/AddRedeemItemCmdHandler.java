package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class AddRedeemItemCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;


    public void handle(AddRedeemItemCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemId());
        redeem.addRedeemItem(cmd.info());
        redeemRepository.update(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }
}
