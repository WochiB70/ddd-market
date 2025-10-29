package xyz.wochib70.domain.redeem.cmd;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Component
public class ModifyRedeemPoolParticipateCmdHandler {

    private final RedeemRepository redeemRepository;

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyRedeemPoolParticipateCmd cmd) {
        Redeem redeem = redeemRepository.findByIdOrThrow(cmd.redeemPoolId());
        IdentifierId<Long> activityId = redeem.getActivityId();
        Activity activity = activityRepository.queryActivityByIdOrThrow(activityId);
        if (!activity.isInitialized()) {
            log.error("修改抽奖活动范围失败，当前活动状态不是INIT，不能修改:[{}]", activity.getActivityId());
            throw new IllegalArgumentException("修改抽奖活动范围失败，当前活动状态不是INIT，不能修改");
        }
        redeem.modifyParticipateScope(cmd.participateScope());
        redeemRepository.update(redeem);
        redeem.getEvents().forEach(eventPublisher::publishEvent);
    }


}
