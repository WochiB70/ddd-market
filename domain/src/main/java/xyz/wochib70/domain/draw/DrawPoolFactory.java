package xyz.wochib70.domain.draw;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class DrawPoolFactory {

    private final DrawPoolIdGenerator drawPoolIdGenerator;

    public DrawPool create(String name, IdentifierId<Long> activityId, DrawStrategyType strategyType) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("名字不能为空");
        }
        ParameterUtil.requireNonNull(activityId, "活动Id不能为空");
        ParameterUtil.requireNonNull(strategyType, "抽奖策略不能为空");
        IdentifierId<Long> drawPoolId = drawPoolIdGenerator.nextAggregateId();
        DrawPoolImpl drawPool = new DrawPoolImpl(drawPoolId);
        drawPool.setName(name);
        drawPool.setActivityId(activityId);
        drawPool.setStrategyType(strategyType);
        return drawPool;
    }
}
