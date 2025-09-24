package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.redeem.Redeem;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemPoolDeletedEvent extends AbstractAggregateEvent<Long> {

    private final Redeem redeem;

    public RedeemPoolDeletedEvent(Redeem redeem) {
        super(RedeemImpl.class, RedeemPoolDeletedEvent.class);
        this.redeem = redeem;
    }
}
