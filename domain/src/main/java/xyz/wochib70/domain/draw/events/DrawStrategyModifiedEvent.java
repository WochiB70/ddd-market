package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;
import xyz.wochib70.domain.draw.DrawStrategyType;

@Getter
public class DrawStrategyModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final DrawStrategyType drawStrategyType;

    public DrawStrategyModifiedEvent(IdentifierId<Long> drawPoolId, DrawStrategyType drawStrategyType) {
        super(DrawPoolImpl.class, DrawStrategyModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.drawStrategyType = drawStrategyType;
    }
}
