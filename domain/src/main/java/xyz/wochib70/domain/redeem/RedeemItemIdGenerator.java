package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.IdentifierId;

public interface RedeemItemIdGenerator {

    IdentifierId<Long> nextRedeemItemId();
}
