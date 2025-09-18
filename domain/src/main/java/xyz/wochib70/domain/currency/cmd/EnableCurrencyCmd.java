package xyz.wochib70.domain.currency.cmd;

import xyz.wochib70.domain.IdentifierId;

public record EnableCurrencyCmd(
        IdentifierId<Long> currencyId
) {
}
