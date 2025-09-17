package xyz.wochib70.domain;


import java.util.*;

/**
 * @author WochiB70
 */
public abstract class AbstractAggregate<ID> implements Aggregate<ID, Long> {

    private final IdentifierId<ID> identifierId;

    private final Deque<AggregateEvent<ID, Long>> events;

    public AbstractAggregate(IdentifierId<ID> identifierId) {
        Objects.requireNonNull(identifierId, "identifierId 不能为null");
        this.identifierId = identifierId;
        this.events = new ArrayDeque<>();
    }

    @Override
    public IdentifierId<ID> identifierId() {
        return identifierId;
    }

    @Override
    public Collection<? super AggregateEvent<ID, Long>> getEvents() {
        return Collections.unmodifiableCollection(events);
    }

    protected void publishEvent(AbstractAggregateEvent<ID> event) {
        events.addLast(event);
    }

}
