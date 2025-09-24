package xyz.wochib70.domain.redeem.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.RedeemImpl;
import xyz.wochib70.domain.redeem.RedeemItemInventory;

@Getter
public class RedeemItemInventoryModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> redeemId;

    private final IdentifierId<Long> redeemItemId;

    private final RedeemItemInventory inventory;

    public RedeemItemInventoryModifiedEvent(
            IdentifierId<Long> redeemId,
            IdentifierId<Long> redeemItemId,
            RedeemItemInventory inventory
    ) {
        super(RedeemImpl.class, RedeemItemInventoryModifiedEvent.class);
        this.redeemId = redeemId;
        this.redeemItemId = redeemItemId;
        this.inventory = inventory;
    }
}
