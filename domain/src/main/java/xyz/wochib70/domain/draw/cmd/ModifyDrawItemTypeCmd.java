package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemType;

public record ModifyDrawItemTypeCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId,
        DrawItemType type
) {
}
