package xyz.wochib70.domain.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.utils.ParameterUtil;

@RequiredArgsConstructor
@Component
public class CurrencyFactory {

    private final CurrencyIdGenerator currencyIdGenerator;

    public Currency create(CurrencyInfo info) {
        ParameterUtil.requireNonNull(info, "CurrencyInfo不能为null");
        CurrencyImpl currency = new CurrencyImpl(
                currencyIdGenerator.nextAggregateId()
        );
        currency.setInfo(info);
        currency.setStatus(CurrencyStatus.VALID);
        currency.setReferenceCount(0);
        currency.create();
        return currency;
    }


}
