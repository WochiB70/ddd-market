package xyz.wochib70.domain.redeem.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.events.ActivityAwardTypeModifiedEvent;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class ActivityAwardTypeModifiedPolicy {

    private final RedeemRepository redeemRepository;

    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void handle(ActivityAwardTypeModifiedEvent event) {
        if (Objects.equals(event.getOldType(), ActivityAwardType.REDEEM)) {
            log.info("检测到活动:[ {} ] 的奖励类型从:[{}]修改为:[{}], 现在需要活清空活动配置的兑换池", event.getActivityId(), event.getOldType(), event.getAwardType());
            List<Redeem> redeems = redeemRepository.findByActivityId(event.getActivityId());
            for (Redeem redeem : redeems) {
                log.info("正在删除活动:[ {} ] 的抽奖池:[ {} ]", event.getActivityId(), redeem.getRedeemId());
                redeem.delete();
                redeemRepository.delete(redeem);
                redeem.getEvents().forEach(eventPublisher::publishEvent);
            }
            log.info("活动:[ {} ] 的兑换池已全部删除", event.getActivityId());
        }
    }
}
