package xyz.wochib70.domain.redeem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class RedeemFactory {

    private final RedeemPoolIdGenerator redeemPoolIdGenerator;

    private final ActivityRepository activityRepository;

    public RedeemImpl createRedeem(
            IdentifierId<Long> activityId,
            String name,
            RedeemParticipateScope scope
    ) {
        ParameterUtil.requireNonBlank(name, "名称不能为空");
        activityRepository.queryActivityByIdOrThrow(activityId);
        RedeemImpl redeem = new RedeemImpl(redeemPoolIdGenerator.nextRedeemPoolId());
        redeem.setName(name);
        redeem.setActivityId(activityId);
        redeem.setRedeemItems(Set.of());
        redeem.setScope(Objects.isNull(scope) ? RedeemParticipateScope.GLOBAL : scope);
        redeem.create();
        return redeem;
    }
}
