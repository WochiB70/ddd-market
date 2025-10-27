package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;
import xyz.wochib70.domain.redeem.RedeemParticipateScope;

@Getter
public class RedeemParticipateScopeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemPoolId;

    private final RedeemParticipateScope participateScope;

    public RedeemParticipateScopeModifiedEvent(
            IdentifierId<Long> redeemPoolId,
            RedeemParticipateScope participateScope
    ) {
        super(RedeemImpl.class, RedeemParticipateScopeModifiedEvent.class);
        this.redeemPoolId = redeemPoolId;
        this.participateScope = participateScope;
    }
}
