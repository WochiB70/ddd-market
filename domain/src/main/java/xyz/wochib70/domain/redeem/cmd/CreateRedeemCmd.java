package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record CreateRedeemCmd(
        IdentifierId<Long> activityId,
        String name
) {
}
