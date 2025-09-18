package xyz.wochib70.domain.currency.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.CurrencyImpl;

@Getter
public class CurrencyEnabledEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> currencyId;

    public CurrencyEnabledEvent(IdentifierId<Long> currencyId) {
        super(CurrencyImpl.class, CurrencyEnabledEvent.class);
        this.currencyId = currencyId;
    }
}
