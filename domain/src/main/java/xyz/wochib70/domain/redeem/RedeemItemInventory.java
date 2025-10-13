package xyz.wochib70.domain.redeem;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

@Setter
@Getter
public class RedeemItemInventory {

    private RedeemItemInventoryType type;

    private Integer validCount;


    public RedeemItemInventory(RedeemItemInventoryType type, Integer validCount) {
        ParameterUtil.requireNonNull(type, "兑换项的库存类型不能为null");
        this.type = type;
        if (!Objects.equals(this.type, RedeemItemInventoryType.INFINITE)
                && (validCount == null || validCount < 0)
        ) {
            throw new IllegalArgumentException("当前的库存类型不是[INFINITE], 库存数量不能为null或是小于0");
        }
        this.validCount = validCount;
    }

    public void redeem(Integer count) {
        if (Objects.equals(type, RedeemItemInventoryType.INFINITE)){
            // 无限库存
            return;
        }
        if (validCount < count) {
            throw new InsufficientRedeemItemInventoryException("库存不足");
        }
        validCount -= count;
    }

    public boolean validInventory() {
        if (type == RedeemItemInventoryType.INFINITE) {
            return true;
        }
        return validCount > 0;
    }
}
