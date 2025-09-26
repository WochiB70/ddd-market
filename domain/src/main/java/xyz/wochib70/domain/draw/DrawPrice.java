package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;

import java.util.Objects;

public record DrawPrice(
        IdentifierId<Long> currencyId,
        Integer price
) {

    public DrawPrice {
        Objects.requireNonNull(currencyId);
        Objects.requireNonNull(price);
        if (price < 0) {
            throw new IllegalArgumentException("抽奖价格不能小于0");
        }
    }
}
