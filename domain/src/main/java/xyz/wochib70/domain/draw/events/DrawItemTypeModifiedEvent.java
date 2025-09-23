package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemType;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemTypeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> awardId;

    private final DrawItemType awardType;

    public DrawItemTypeModifiedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> awardId,
            DrawItemType awardType
    ) {
        super(DrawPoolImpl.class, DrawItemTypeModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.awardId = awardId;
        this.awardType = awardType;
    }
}
