package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

public record RedeemItemPrice(
        IdentifierId<Long> currencyId,
        Integer price
) {

    public RedeemItemPrice {
        ParameterUtil.requireNonNull(currencyId, "货币Id不能为null");
        if (Objects.isNull(price) || price < 0) {
            throw new IllegalArgumentException("价格不能为null或者小于0");
        }
    }
}
