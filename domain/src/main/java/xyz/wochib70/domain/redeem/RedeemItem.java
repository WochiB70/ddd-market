package xyz.wochib70.domain.redeem;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.IdentifierId;

import java.util.Objects;

@Getter
public class RedeemItem {

    private IdentifierId<Long> id;

    private String name;

    private String description;

    private RedeemItemInventory inventory;

    private RedeemItemPrice price;

    private RedeemItemType type;


    public void redeem(int count) {
        inventory.redeem(count);
    }

    public void setId(IdentifierId<Long> id) {
        this.id = Objects.requireNonNull(id, "兑换项的id不能为null");
    }

    public void setItemInfo(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("兑换项的名称不能为null或空");
        }
        this.name = name;
        this.description = description;
    }

    public void setItemType(RedeemItemType type) {
        this.type = Objects.requireNonNull(type, "兑换项的类型不能为null");
    }

    public void setItemPrice(RedeemItemPrice price) {
        Objects.requireNonNull(price, "兑换项的价格不能为null");
        this.price = price;
    }


    public void setInventory(RedeemItemInventory inventory) {
        this.inventory = Objects.requireNonNull(inventory, "兑换项的库存不能为null");
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RedeemItem that = (RedeemItem) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
