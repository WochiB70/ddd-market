package xyz.wochib70.domain.redeem.cmd;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

@RequiredArgsConstructor
@Service
public class DeletedRedeemCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(DeletedRedeemCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemId());
        redeem.delete();
        redeemRepository.delete(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }
}
