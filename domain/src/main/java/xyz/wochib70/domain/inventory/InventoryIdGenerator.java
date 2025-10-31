package xyz.wochib70.domain.inventory;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface InventoryIdGenerator extends DomainIdGenerator<Long> {


    default IdentifierId<Long> nextInventoryId() {
        return nextAggregateId("inventory");
    }
}
