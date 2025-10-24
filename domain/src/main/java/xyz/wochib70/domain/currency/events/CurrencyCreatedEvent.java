package xyz.wochib70.domain.currency.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.Currency;
import xyz.wochib70.domain.currency.CurrencyImpl;
import xyz.wochib70.domain.currency.CurrencyStatus;

/**
 * @author WochiB70
 */
@Getter
public class CurrencyCreatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> id;

    private final String name;

    private final String description;

    private final CurrencyStatus status;

    private final Integer referenceCount;

    public CurrencyCreatedEvent(
            IdentifierId<Long> id,
            String name,
            String description,
            CurrencyStatus status,
            Integer referenceCount
    ) {
        super(CurrencyImpl.class, CurrencyCreatedEvent.class);
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.referenceCount = referenceCount;
    }
}
