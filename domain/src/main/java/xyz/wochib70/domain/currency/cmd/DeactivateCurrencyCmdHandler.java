package xyz.wochib70.domain.currency.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.currency.CurrencyRepository;

@RequiredArgsConstructor
@Component
public class DeactivateCurrencyCmdHandler {


    private final CurrencyRepository currencyRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void handle(DeactivateCurrencyCmd cmd) {
        var currency = currencyRepository.findById(cmd.currencyId());
        currency.deactivate();
        currency.getEvents().forEach(applicationEventPublisher::publishEvent);
        currencyRepository.save(currency);
    }
}
