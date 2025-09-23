package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;

public record Reward(
        IdentifierId<Long> awardId,
        Integer count
) {

}
