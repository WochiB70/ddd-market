package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawStrategyType;

public record ModifyDrawPoolStrategyCmd(
        IdentifierId<Long> drawPoolId,
        DrawStrategyType strategyType
) {
}
