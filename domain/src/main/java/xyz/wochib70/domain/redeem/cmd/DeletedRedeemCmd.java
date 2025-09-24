package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;

public record DeletedRedeemCmd(
        IdentifierId<Long> redeemId
) {
}
