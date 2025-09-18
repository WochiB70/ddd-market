package xyz.wochib70.domain.currency.cmd;

import xyz.wochib70.domain.currency.CurrencyInfo;

public record CreateCurrencyCmd(
    CurrencyInfo info
) {
}
