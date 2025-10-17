package xyz.wochib70.domain;

import xyz.wochib70.domain.utils.ParameterUtil;

import java.time.LocalDateTime;

/**
 * 由于事件只存在于本系统内部，一般不会去对接外部的系统，所以事件ID可以在Type模块的Abstract层面直接确定使用Long
 *
 * @author WochiB70
 */
public abstract class AbstractAggregateEvent<AggregateID> implements AggregateEvent<AggregateID, Long> {

    private final IdentifierId<Long> eventId;

    private final LocalDateTime createTime;

    private final Class<? extends Aggregate<AggregateID, Long>> aggregateClass;

    private final Class<? extends AggregateEvent<AggregateID, Long>> eventClass;

    public AbstractAggregateEvent(
            Class<? extends Aggregate<AggregateID, Long>> aggregateClass,
            Class<? extends AggregateEvent<AggregateID, Long>> eventClass
    ) {
        ParameterUtil.requireNonNull(aggregateClass, "聚合类型的Class不能为null");
        ParameterUtil.requireNonNull(eventClass, "事件类型的Class不能为null");
        this.eventId = DomainIdRegistry.nextEventId();
        this.aggregateClass = aggregateClass;
        this.eventClass = eventClass;
        this.createTime = LocalDateTime.now();
    }

    @Override
    public LocalDateTime createTime() {
        return createTime;
    }

    @Override
    public IdentifierId<Long> eventId() {
        return eventId;
    }

    @Override
    public Class<? extends Aggregate<AggregateID, Long>> aggregateClass() {
        return aggregateClass;
    }

    @Override
    public Class<? extends AggregateEvent<AggregateID, Long>> eventClass() {
        return eventClass;
    }
}
