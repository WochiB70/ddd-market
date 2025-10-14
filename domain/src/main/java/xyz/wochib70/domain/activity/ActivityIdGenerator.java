package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface ActivityIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextActivityId() {
        return nextAggregateId("activity");
    }
}
