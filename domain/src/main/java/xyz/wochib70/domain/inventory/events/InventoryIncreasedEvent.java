package xyz.wochib70.domain.inventory.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.InventoryImpl;

@Getter
public class InventoryIncreasedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> inventoryId;

    private final IdentifierId<Long> goodsId;

    private final GoodsType type;

    private final Integer count;

    public InventoryIncreasedEvent(
            IdentifierId<Long> inventoryId,
            IdentifierId<Long> goodsId,
            GoodsType type,
            Integer count
    ) {
        super(InventoryImpl.class, InventoryIncreasedEvent.class);
        this.inventoryId = inventoryId;
        this.goodsId = goodsId;
        this.type = type;
        this.count = count;
    }


}
