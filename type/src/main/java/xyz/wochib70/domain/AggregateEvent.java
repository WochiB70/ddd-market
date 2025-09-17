package xyz.wochib70.domain;

import java.time.LocalDateTime;

/**
 * @author WochiB70
 */
public interface AggregateEvent<AggregateID, EventID> {

    /**
     * 时间发生的时间
     *
     * @return LocalDateTime
     */
    LocalDateTime createTime();

    /**
     * 时间Id
     *
     * @return IdentifierId
     */
    IdentifierId<EventID> eventId();

    /**
     * 事件的类型
     *
     * @return Class
     */
    Class<? extends AggregateEvent<AggregateID, EventID>> eventClass();

    /**
     * 聚合的类型
     *
     * @return Class
     */
    Class<? extends Aggregate<AggregateID, EventID>> aggregateClass();
}
