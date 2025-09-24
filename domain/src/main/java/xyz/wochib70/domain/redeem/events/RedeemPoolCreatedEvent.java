package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemPoolCreatedEvent extends AbstractAggregateEvent<Long> {

    private final Redeem redeem;

    public RedeemPoolCreatedEvent(Redeem redeem) {
        super(RedeemImpl.class, RedeemPoolCreatedEvent.class);
        this.redeem = redeem;

    }
}
