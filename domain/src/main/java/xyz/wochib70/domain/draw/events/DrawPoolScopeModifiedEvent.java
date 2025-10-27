package xyz.wochib70.domain.draw.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolImpl;
import xyz.wochib70.domain.draw.DrawPoolParticipateScope;

@Getter
public class DrawPoolScopeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> drawPoolId;

    private final DrawPoolParticipateScope scope;

    public DrawPoolScopeModifiedEvent(
            IdentifierId<Long> drawPoolId,
            DrawPoolParticipateScope scope
    ) {
        super(DrawPoolImpl.class, DrawPoolScopeModifiedEvent.class);
        this.drawPoolId = drawPoolId;
        this.scope = scope;
    }
}
