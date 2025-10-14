package xyz.wochib70.domain.account;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface AccountIdGenerator extends DomainIdGenerator<Long> {


    default IdentifierId<Long> nextAccountId() {
        return nextAggregateId("account");
    }
}
