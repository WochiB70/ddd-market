package xyz.wochib70.domain.draw.cmd;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityRepository;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolRepository;

import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Component
public class ModifyDrawPoolParticipateScopeCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ActivityRepository activityRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyDrawPoolParticipateScopeCmd cmd) {
        DrawPool drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        IdentifierId<Long> activityId = drawPool.getActivityId();
        Activity activity = activityRepository.queryActivityByIdOrThrow(activityId);
        if (!activity.isInitialized()) {
            log.error("修改抽奖活动范围失败，当前活动状态不是INIT，不能修改:[{}]", activity.getActivityId());
            throw new IllegalArgumentException("修改抽奖活动范围失败，当前活动状态不是INIT，不能修改");
        }
        drawPool.modifyParticipateScope(cmd.scope());
        drawPoolRepository.update(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
    }

}
