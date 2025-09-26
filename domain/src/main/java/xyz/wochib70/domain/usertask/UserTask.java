package xyz.wochib70.domain.usertask;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;

public sealed interface UserTask extends Aggregate<Long, Long> permits UserTaskImpl {

    IdentifierId<Long> getUserTaskId();

    void complete();
}
