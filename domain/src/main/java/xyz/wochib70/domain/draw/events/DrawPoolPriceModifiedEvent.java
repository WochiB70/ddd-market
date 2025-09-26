package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;
import xyz.wochib70.domain.draw.DrawPrice;

@Getter
public class DrawPoolPriceModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final DrawPrice price;

    public DrawPoolPriceModifiedEvent(IdentifierId<Long> drawPoolId, DrawPrice price) {
        super(DrawPoolImpl.class, DrawPoolPriceModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.price = price;
    }
}
