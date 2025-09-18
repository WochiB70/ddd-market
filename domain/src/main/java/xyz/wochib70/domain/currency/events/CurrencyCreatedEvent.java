package xyz.wochib70.domain.currency.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.currency.Currency;
import xyz.wochib70.domain.currency.CurrencyImpl;

/**
 * @author WochiB70
 */
@Getter
public class CurrencyCreatedEvent extends AbstractAggregateEvent<Long> {

    private final Currency currency;

    public CurrencyCreatedEvent(Currency currency) {
        super(CurrencyImpl.class, CurrencyCreatedEvent.class);
        this.currency = currency;
    }
}
