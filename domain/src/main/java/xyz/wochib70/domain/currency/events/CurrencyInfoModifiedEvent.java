package xyz.wochib70.domain.currency.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.CurrencyImpl;
import xyz.wochib70.domain.currency.CurrencyInfo;

/**
 * @author WochiB70
 */
@Getter
public class CurrencyInfoModifiedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> currencyId;

    private final CurrencyInfo info;

    public CurrencyInfoModifiedEvent(
            IdentifierId<Long> currencyId,
            CurrencyInfo info
    ) {
        super(CurrencyImpl.class, CurrencyInfoModifiedEvent.class);
        this.currencyId = currencyId;
        this.info = info;
    }
}
