package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemItemPrice;

public record ModifyRedeemItemPriceCmd(
        IdentifierId<Long> redeemId,
        IdentifierId<Long> redeemItemId,
        RedeemItemPrice price
) {
}
