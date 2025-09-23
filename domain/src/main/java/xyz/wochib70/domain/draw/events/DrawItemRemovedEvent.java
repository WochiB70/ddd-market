package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemRemovedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> awardId;

    public DrawItemRemovedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> awardId
    ) {
        super(DrawPoolImpl.class, DrawItemRemovedEvent.class);
        this.drawPoolId = drawPoolId;
        this.awardId = awardId;
    }
}
