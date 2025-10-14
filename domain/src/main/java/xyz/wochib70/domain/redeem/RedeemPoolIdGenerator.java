package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface RedeemPoolIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextRedeemPoolId() {
        return nextAggregateId("redeem_pool");
    }
}
