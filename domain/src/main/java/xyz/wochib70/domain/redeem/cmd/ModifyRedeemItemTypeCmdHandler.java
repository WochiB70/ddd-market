package xyz.wochib70.domain.redeem.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class ModifyRedeemItemTypeCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyRedeemItemTypeCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemId());
        redeem.modifyRedeemItemType(cmd.redeemItemId(), cmd.redeemItemType());
        redeemRepository.update(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }
}
