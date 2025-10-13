package xyz.wochib70.domain.currency;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;

class CurrencyFailTest extends AggregateTestBase {

    @Test
    void createCurrencyWithNullInfoTest() {

        try {
            CurrencyIdGenerator currencyIdGenerator = Mockito.mock(CurrencyIdGenerator.class);

            Mockito.when(currencyIdGenerator.nextAggregateId())
                    .thenReturn(new DefaultIdentifierId<>(1L));


            CurrencyFactory factory = new CurrencyFactory(currencyIdGenerator);

            factory.create(null);

        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "CCurrencyInfo不能为null");
        }
    }

    @Test
    void modifyCurrencyInfoWithNullInfoTest() {
        try {
            CurrencyImpl currency = new CurrencyImpl(new DefaultIdentifierId<>(1L));
            currency.modifyInfo(null);
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "CurrencyInfo不能为null");
        }
    }
}
