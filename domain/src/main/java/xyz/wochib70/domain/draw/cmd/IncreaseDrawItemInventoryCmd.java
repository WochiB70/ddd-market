package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;

public record IncreaseDrawItemInventoryCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId,
        Integer increase
) {
}
