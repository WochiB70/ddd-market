package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemNameModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final String name;

    public RedeemNameModifiedEvent(IdentifierId<Long> redeemId, String name) {
        super(RedeemImpl.class, RedeemNameModifiedEvent.class);
        this.redeemId = redeemId;
        this.name = name;
    }
}
