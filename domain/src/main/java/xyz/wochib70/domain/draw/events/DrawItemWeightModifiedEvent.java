package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemWeightModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> awardId;

    private final Integer weight;

    public DrawItemWeightModifiedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> awardId,
            Integer weight
    ) {
        super(DrawPoolImpl.class, DrawItemWeightModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.awardId = awardId;
        this.weight = weight;
    }
}
