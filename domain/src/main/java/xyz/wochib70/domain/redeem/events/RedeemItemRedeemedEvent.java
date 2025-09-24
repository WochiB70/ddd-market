package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemItemRedeemedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    private final Integer quantity;

    private final UserId userId;

    public RedeemItemRedeemedEvent(
            IdentifierId<Long> redeemId,
            IdentifierId<Long> redeemItemId,
            Integer quantity,
            UserId userId
    ) {
        super(RedeemImpl.class, RedeemItemRedeemedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
        this.quantity = quantity;
        this.userId = userId;
    }
}
