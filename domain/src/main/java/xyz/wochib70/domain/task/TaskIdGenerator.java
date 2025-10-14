package xyz.wochib70.domain.task;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface TaskIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextTaskId() {
        return nextAggregateId("task");
    }

}
