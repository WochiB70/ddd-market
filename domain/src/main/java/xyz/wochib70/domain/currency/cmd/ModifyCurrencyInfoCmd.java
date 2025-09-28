package xyz.wochib70.domain.currency.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.CurrencyInfo;

public record ModifyCurrencyInfoCmd(
        IdentifierId<Long> currencyId,
        CurrencyInfo info
) {
}
