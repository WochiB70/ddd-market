package xyz.wochib70.domain.redeem.cmd;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@AllArgsConstructor
@Component
public class ModifyRedeemPoolParticipateCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyRedeemPoolParticipateCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemPoolId());
        redeem.modifyParticipateScope(cmd.participateScope());
        redeemRepository.update(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }


}
