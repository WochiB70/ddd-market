package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;

public record DecreaseDrawItemInventoryCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> drawItemId,
        Integer decrease
) {
}
