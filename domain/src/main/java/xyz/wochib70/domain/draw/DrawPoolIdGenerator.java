package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface DrawPoolIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextDrawPoolId() {
        return nextAggregateId("draw_pool");
    }
}
