package xyz.wochib70.domain.currency.cmd;

import xyz.wochib70.domain.IdentifierId;

public record DeactivateCurrencyCmd(
        IdentifierId<Long> currencyId
) {
}
