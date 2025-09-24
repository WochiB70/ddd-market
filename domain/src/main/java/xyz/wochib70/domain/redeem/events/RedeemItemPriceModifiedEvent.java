package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;
import xyz.wochib70.domain.redeem.RedeemItemPrice;

@Getter
public class RedeemItemPriceModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    private final RedeemItemPrice price;


    public RedeemItemPriceModifiedEvent(
            IdentifierId<Long> redeemId,
            IdentifierId<Long> redeemItemId,
            RedeemItemPrice price
    ) {
        super(RedeemImpl.class, RedeemItemPriceModifiedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
        this.price = price;
    }
}
