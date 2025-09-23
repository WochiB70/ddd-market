package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;

public record RemoveDrawItemCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId
) {
}
