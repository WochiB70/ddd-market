package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyDrawItemBasicInfoCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId,
        String name,
        String description
) {
}
