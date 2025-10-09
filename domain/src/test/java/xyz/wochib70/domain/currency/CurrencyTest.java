package xyz.wochib70.domain.currency;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.currency.events.CurrencyDeactivatedEvent;
import xyz.wochib70.domain.currency.events.CurrencyEnabledEvent;
import xyz.wochib70.domain.currency.events.CurrencyInfoModifiedEvent;

class CurrencyTest extends AggregateTestBase {

    @Test
    void createCurrencyTest() {
        CurrencyIdGenerator currencyIdGenerator = Mockito.mock(CurrencyIdGenerator.class);

        Mockito.when(currencyIdGenerator.nextAggregateId())
                .thenReturn(new DefaultIdentifierId<>(1L));


        CurrencyFactory factory = new CurrencyFactory(currencyIdGenerator);

        Currency currency = factory.create(new CurrencyInfo("USD", "Dollar"));

        Assert.isTrue(currency.getCurrencyId().getId() == 1L, "Currency Id should be 1");
    }

    @Test
    void modifyCurrencyInfoTest() {
        CurrencyImpl currency = new CurrencyImpl(new DefaultIdentifierId<>(1L));

        currency.modifyInfo(new CurrencyInfo("USD", "Dollar"));

        for (Object event : currency.getEvents()) {
            Assert.isTrue(event instanceof CurrencyInfoModifiedEvent, "Event should be CurrencyInfoModifiedEvent");
            Assert.isTrue(((CurrencyInfoModifiedEvent) event).getInfo().name().equals("USD"), "Currency Info name should be USD");
        }
    }

    @Test
    void enableCurrencyTest() {
        CurrencyImpl currency = new CurrencyImpl(new DefaultIdentifierId<>(1L));
        currency.setStatus(CurrencyStatus.INVALID);

        currency.enable();

        for (Object event : currency.getEvents()) {
            Assert.isTrue(event instanceof CurrencyEnabledEvent, "Event should be CurrencyEnabledEvent");
        }
    }

    @Test
    void deactivateCurrencyTest() {
        CurrencyImpl currency = new CurrencyImpl(new DefaultIdentifierId<>(1L));
        currency.setStatus(CurrencyStatus.VALID);

        currency.deactivate();

        for (Object event : currency.getEvents()) {
            Assert.isTrue(event instanceof CurrencyDeactivatedEvent, "Event should be CurrencyDeactivatedEvent");
        }
    }
}
