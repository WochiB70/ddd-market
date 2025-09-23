package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemInventory;

public record ModifyDrawItemInventoryCmd(
        IdentifierId<Long> drawPoolId,
        IdentifierId<Long> awardId,
        DrawItemInventory inventory
) {
}
