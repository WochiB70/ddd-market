package xyz.wochib70.domain.usertask;

import xyz.wochib70.domain.DomainIdGenerator;
import xyz.wochib70.domain.IdentifierId;

public interface UserTaskIdGenerator extends DomainIdGenerator<Long> {

    default IdentifierId<Long> nextUserTaskId() {
        return nextAggregateId("user_task");
    }
}
