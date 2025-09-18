package xyz.wochib70.domain.currency.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.CurrencyImpl;

@Getter
public class CurrencyDeactivatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> currencyId;


    public CurrencyDeactivatedEvent(IdentifierId<Long> currencyId) {
        super(CurrencyImpl.class, CurrencyDeactivatedEvent.class);
        this.currencyId = currencyId;
    }
}
