package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;

@Getter
public class RedeemItemInfoModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    private final String name;

    private final String description;

    public RedeemItemInfoModifiedEvent(
            IdentifierId<Long> redeemId,
            IdentifierId<Long> redeemItemId,
            String name,
            String description
    ) {
        super(RedeemImpl.class, RedeemItemInfoModifiedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
        this.name = name;
        this.description = description;
    }
}
