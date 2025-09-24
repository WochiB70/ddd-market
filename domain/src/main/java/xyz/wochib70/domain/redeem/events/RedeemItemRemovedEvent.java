package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemItemRemovedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    public RedeemItemRemovedEvent(IdentifierId<Long> redeemId, IdentifierId<Long> redeemItemId) {
        super(RedeemImpl.class, RedeemItemRemovedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
    }
}
