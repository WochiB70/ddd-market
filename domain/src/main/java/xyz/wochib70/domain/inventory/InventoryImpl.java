package xyz.wochib70.domain.inventory;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.events.*;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;


@Getter
@Setter
public non-sealed class InventoryImpl extends AbstractAggregate<Long> implements Inventory {

    private IdentifierId<Long> goodsId;

    private GoodsType goodsType;

    private InventoryType type;

    private Integer count;


    public InventoryImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getInventoryId() {
        return identifierId();
    }

    @Override
    public void modifyInventory(InventoryType type) {
        ParameterUtil.requireNonNull(type, "InventoryType不能为空");
        if (!Objects.equals(this.type, type)) {
            this.type = type;
            this.count = 0;
            publishEvent(new InventoryModifiedEvent(
                    getInventoryId(),
                    type,
                    count
            ));
        }
    }

    @Override
    public void useInventory(int count) {
        ParameterUtil.requireExpression(count <= 0, "不能使用小于0的库存");
        if (this.type == InventoryType.LIMITED && this.count < count) {
            throw new InsufficientInventoryException("库存不足");
        }
        this.count -= count;
        publishEvent(new InventoryUsedEvent(
                getInventoryId(),
                type,
                count
        ));
    }

    @Override
    public void decreaseInventory(Integer count) {
        ParameterUtil.requireNonNull(count, "库存不能为空");
        if (this.type == InventoryType.INFINITE) {
            return;
        }
        this.count -= count;
        publishEvent(new InventoryDecreasedEvent(
                getInventoryId(),
                goodsId,
                goodsType,
                count
        ));
    }

    @Override
    public void increaseInventory(Integer count) {
        ParameterUtil.requireNonNull(count, "库存不能为空");
        if (this.type == InventoryType.INFINITE) {
            return;
        }
        this.count += count;
        publishEvent(new InventoryIncreasedEvent(
                getInventoryId(),
                goodsId,
                goodsType,
                count
        ));
    }

    @Override
    public void create() {
        publishEvent(new InventoryCreatedEvent(
                this
        ));
    }
}
