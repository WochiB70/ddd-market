package xyz.wochib70.domain;


import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author WochiB70
 */
public abstract class AbstractAggregate<ID> implements Aggregate<ID, Long> {

    private final IdentifierId<ID> identifierId;

    private final List<AggregateEvent<ID, Long>> events;

    public AbstractAggregate(IdentifierId<ID> identifierId) {
        ParameterUtil.requireNonNull(identifierId, "identifierId 不能为null");
        this.identifierId = identifierId;
        this.events = new ArrayList<>(2);
    }

    @Override
    public IdentifierId<ID> identifierId() {
        return identifierId;
    }

    @Override
    public List<? super AggregateEvent<ID, Long>> getEvents() {
        return Collections.unmodifiableList( events);
    }

    protected void publishEvent(AbstractAggregateEvent<ID> event) {
        events.addLast(event);
    }

}
