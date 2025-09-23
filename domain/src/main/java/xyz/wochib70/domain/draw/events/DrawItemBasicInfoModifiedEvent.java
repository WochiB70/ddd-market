package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class DrawItemBasicInfoModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> awardId;

    private final String name;

    private final String description;

    public DrawItemBasicInfoModifiedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> awardId,
            String name,
            String description
    ) {
        super(DrawPoolImpl.class, DrawItemBasicInfoModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.awardId = awardId;
        this.name = name;
        this.description = description;
    }
}
