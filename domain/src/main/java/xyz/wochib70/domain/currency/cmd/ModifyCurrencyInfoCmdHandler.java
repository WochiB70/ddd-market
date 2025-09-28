package xyz.wochib70.domain.currency.cmd;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.currency.Currency;
import xyz.wochib70.domain.currency.CurrencyRepository;

@RequiredArgsConstructor
@Service
public class ModifyCurrencyInfoCmdHandler {

    private final CurrencyRepository currencyRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyCurrencyInfoCmd cmd) {
        Currency currency = currencyRepository.findById(cmd.currencyId());
        currency.modifyInfo(cmd.info());
        currencyRepository.save(currency);
        currency.getEvents().forEach(eventPublisher::publishEvent);
    }
}
