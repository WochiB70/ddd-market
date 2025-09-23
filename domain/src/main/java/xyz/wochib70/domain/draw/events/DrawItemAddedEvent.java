package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItem;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemAddedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final DrawItem drawItem;


    public DrawItemAddedEvent(IdentifierId<Long> drawPoolId, DrawItem drawItem) {
        super(DrawPoolImpl.class, DrawItemAddedEvent.class);
        this.drawPoolId = drawPoolId;
        this.drawItem = drawItem;
    }
}
