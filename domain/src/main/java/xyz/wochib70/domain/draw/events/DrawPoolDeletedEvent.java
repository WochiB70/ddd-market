package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawPoolDeletedEvent extends AbstractAggregateEvent<Long> {

    private final DrawPoolImpl drawPool;

    public DrawPoolDeletedEvent(DrawPoolImpl drawPool) {
        super(DrawPoolImpl.class, DrawPoolDeletedEvent.class);
        this.drawPool = drawPool;
    }
}
