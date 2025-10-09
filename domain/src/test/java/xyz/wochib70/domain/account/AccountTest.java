package xyz.wochib70.domain.account;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.account.events.AccountBalanceDecreasedEvent;
import xyz.wochib70.domain.account.events.AccountBalanceIncreasedEvent;

class AccountTest extends AggregateTestBase {


    @Test
    void decreaseBalanceTest() {
        AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(1L));
        account.setBalance(1000);
        account.decreaseBalance(100);
        for (Object event : account.getEvents()) {
            Assert.isTrue(event instanceof AccountBalanceDecreasedEvent, "Event should be AccountBalanceDecreasedEvent");
            Assert.isTrue(((AccountBalanceDecreasedEvent) event).getDecreasedAmount() == 100, "Decreased amount should be 100");
        }
    }

    @Test
    void increaseBalanceTest() {
        AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(1L));
        account.setBalance(1000);
        account.increateBalance(100);

        for (Object event : account.getEvents()) {
            Assert.isTrue(event instanceof AccountBalanceIncreasedEvent, "Event should be AccountBalanceDecreasedEvent");
            Assert.isTrue(((AccountBalanceIncreasedEvent) event).getIncreasedAmount() == 100, "Decreased amount should be 100");
        }
    }

    @Test
    void decreaseBalanceWithInsufficientBalanceTest() {
        try {
            AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(1L));
            account.setBalance(50);
            account.decreaseBalance(100);
        } catch (Exception e) {
            Assert.isTrue(e instanceof InsufficientBalanceException, "InsufficientBalanceException should be thrown");
        }
    }

    @Test
    void decreaseBalanceWithNegativeAmountTest() {
        try {
            AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(1L));
            account.setBalance(50);
            account.decreaseBalance(-100);
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "IllegalArgumentException should be thrown");
        }
    }

    @Test
    void increaseBalanceWithNegativeAmountTest() {
        try {
            AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(1L));
            account.setBalance(50);
            account.increateBalance(-100);
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "IllegalArgumentException should be thrown");
        }
    }
}
