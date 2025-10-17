package xyz.wochib70.domain.currency.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.AggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.Currency;
import xyz.wochib70.domain.currency.CurrencyFactory;
import xyz.wochib70.domain.currency.CurrencyRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Component
public class CreateCurrencyCmdHandler {

    private final CurrencyFactory currencyFactory;

    private final CurrencyRepository currencyRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public IdentifierId<Long> handle(CreateCurrencyCmd cmd) {
        Currency currency = currencyFactory.create(cmd.info());
        currencyRepository.save(currency);
        Collection<? super AggregateEvent<Long, Long>> events = currency.getEvents();
        events.forEach(applicationEventPublisher::publishEvent);
        return currency.getCurrencyId();
    }

}
