package xyz.wochib70.domain.redeem.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemItemType;

public record ModifyRedeemItemTypeCmd(
        IdentifierId< Long> redeemId,
        IdentifierId< Long> redeemItemId,
        RedeemItemType redeemItemType
) {
}
