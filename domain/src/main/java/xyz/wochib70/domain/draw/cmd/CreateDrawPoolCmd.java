package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawStrategyType;

public record CreateDrawPoolCmd(
        String name,
        IdentifierId<Long> activityId,
        DrawStrategyType strategyType
) {
}
