package xyz.wochib70.domain.currency;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface CurrencyIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextCurrencyId() {
        return nextAggregateId("currency");
    }
}
