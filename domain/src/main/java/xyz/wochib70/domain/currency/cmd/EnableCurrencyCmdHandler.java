package xyz.wochib70.domain.currency.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.currency.CurrencyRepository;

@RequiredArgsConstructor
@Component
public class EnableCurrencyCmdHandler {

    private final CurrencyRepository currencyRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void handle(EnableCurrencyCmd cmd) {
        var currency = currencyRepository.findByIdOrThrow(cmd.currencyId());
        currency.enable();
        currency.getEvents().forEach(applicationEventPublisher::publishEvent);
        currencyRepository.save(currency);
    }
}
