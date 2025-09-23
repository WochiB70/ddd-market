package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawPoolNameModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final String name;

    public DrawPoolNameModifiedEvent(IdentifierId<Long> drawPoolId, String name) {
        super(DrawPoolImpl.class, DrawPoolNameModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.name = name;
    }
}
