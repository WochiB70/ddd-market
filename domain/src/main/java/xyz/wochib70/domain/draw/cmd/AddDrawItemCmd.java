package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemInfo;

public record AddDrawItemCmd(
        IdentifierId<Long> drawPoolId,
        DrawItemInfo drawItemInfo
) {
}
