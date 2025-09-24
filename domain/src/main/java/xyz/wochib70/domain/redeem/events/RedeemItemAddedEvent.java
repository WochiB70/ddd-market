package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;
import xyz.wochib70.domain.redeem.RedeemItem;

@Getter
public class RedeemItemAddedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final RedeemItem redeemItem;

    public RedeemItemAddedEvent(IdentifierId<Long> redeemId, RedeemItem redeemItem) {
        super(RedeemImpl.class, RedeemItemAddedEvent.class);
        this.redeemId = redeemId;
        this.redeemItem = redeemItem;
    }
}
