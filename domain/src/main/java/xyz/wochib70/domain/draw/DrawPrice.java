package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

public record DrawPrice(
        IdentifierId<Long> currencyId,
        Integer price
) {

    public DrawPrice {
        ParameterUtil.requireNonNull(currencyId);
        ParameterUtil.requireNonNull(price);
        if (price < 0) {
            throw new IllegalArgumentException("抽奖价格不能小于0");
        }
    }
}
