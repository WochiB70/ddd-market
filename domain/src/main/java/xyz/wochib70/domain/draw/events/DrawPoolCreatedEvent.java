package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawPoolCreatedEvent extends AbstractAggregateEvent<Long> {

    private final DrawPoolImpl drawPool;


    public DrawPoolCreatedEvent(DrawPoolImpl drawPool) {
        super(DrawPoolImpl.class, DrawPoolCreatedEvent.class);
        this.drawPool = drawPool;
    }
}
