package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;
import xyz.wochib70.domain.redeem.RedeemItemType;

@Getter
public class RedeemItemTypeModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    private final RedeemItemType type;

    public RedeemItemTypeModifiedEvent(
            IdentifierId<Long> redeemId,
            IdentifierId<Long> redeemItemId,
            RedeemItemType type
    ) {
        super(RedeemImpl.class, RedeemItemTypeModifiedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
        this.type = type;
    }
}
