package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemItemInfo;

public record AddRedeemItemCmd(
        IdentifierId<Long> redeemId,
        RedeemItemInfo info
) {
}
