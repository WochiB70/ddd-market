package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemInventory;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemInventoryModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> awardId;

    private final DrawItemInventory drawItemInventory;

    public DrawItemInventoryModifiedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> awardId,
            DrawItemInventory drawItemInventory
    ) {
        super(DrawPoolImpl.class, DrawItemInventoryModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.awardId = awardId;
        this.drawItemInventory = drawItemInventory;
    }
}
