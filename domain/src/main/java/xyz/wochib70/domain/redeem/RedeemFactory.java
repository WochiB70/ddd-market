package xyz.wochib70.domain.redeem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityRepository;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class RedeemFactory {

    private final RedeemItemIdGenerator redeemItemIdGenerator;

    private final ActivityRepository activityRepository;

    public RedeemImpl createRedeem(IdentifierId<Long> activityId, String name) {
        activityRepository.queryActivityByIdOrThrow(activityId);
        RedeemImpl redeem = new RedeemImpl(redeemItemIdGenerator.nextRedeemItemId());
        redeem.setName(name);
        redeem.setRedeemItems(Set.of());
        redeem.create();
        return redeem;
    }
}
