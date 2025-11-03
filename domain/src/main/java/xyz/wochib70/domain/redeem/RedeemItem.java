package xyz.wochib70.domain.redeem;

import lombok.Getter;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

@Getter
public class RedeemItem {

    private IdentifierId<Long> id;

    private String name;

    private String description;

    private RedeemItemPrice price;

    private RedeemItemType type;



    public void setId(IdentifierId<Long> id) {
        ParameterUtil.requireNonNull(id, "兑换项的id不能为null");
        this.id = id;
    }

    public void setItemInfo(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("兑换项的名称不能为null或空");
        }
        this.name = name;
        this.description = description;
    }

    public void setItemType(RedeemItemType type) {
        ParameterUtil.requireNonNull(type, "兑换项的类型不能为null");
        this.type = type;
    }

    public void setItemPrice(RedeemItemPrice price) {
        ParameterUtil.requireNonNull(price, "兑换项的价格不能为null");
        this.price = price;
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
