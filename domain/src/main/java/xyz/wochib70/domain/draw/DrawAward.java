package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;

public record DrawAward(
        IdentifierId<Long> awardId,
        DrawItemType awardType
) {
}
