package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPrice;

public record ModifyDrawPoolPriceCmd(
        IdentifierId<Long> drawPoolId,
        DrawPrice drawPrice
) {
}
