package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawItemType;
import xyz.wochib70.domain.draw.DrawPoolImpl;

@Getter
public class AwardReceivedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final IdentifierId<Long> activityId;

    private final IdentifierId<Long> awardId;

    private final DrawItemType awardType;

    private final IdentifierId<Long> userId;

    private final Integer count;

    public AwardReceivedEvent(
            IdentifierId<Long> drawPoolId,
            IdentifierId<Long> activityId,
            IdentifierId<Long> awardId,
            DrawItemType awardType,
            IdentifierId<Long> userId,
            Integer count
    ) {
        super(DrawPoolImpl.class, AwardReceivedEvent.class);
        this.drawPoolId = drawPoolId;
        this.activityId = activityId;
        this.awardId = awardId;
        this.awardType = awardType;
        this.userId = userId;
        this.count = count;
    }
}
