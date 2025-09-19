package xyz.wochib70.domain.account;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.account.events.AccountBalanceDecreasedEvent;
import xyz.wochib70.domain.account.events.AccountBalanceIncreasedEvent;
import xyz.wochib70.domain.account.events.AccountCreatedEvent;

@Getter
@Setter
public non-sealed class AccountImpl extends AbstractAggregate<Long> implements Account {

    private UserId userId;

    private IdentifierId<Long> currencyId;

    private int balance;

    public AccountImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getAccountId() {
        return identifierId();
    }

    @Override
    public IdentifierId<Long> getUserId() {
        return userId;
    }

    @Override
    public void increateBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("增加的金额必须大于0");
        }
        balance += amount;
        publishEvent(new AccountBalanceIncreasedEvent(
                this.getAccountId(),
                amount
        ));
    }

    @Override
    public void decreaseBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("减少的金额必须大于0");
        }
        if (this.balance < amount) {
            throw new InsufficientBalanceException("余额不足");
        }
        balance -= amount;
        publishEvent(new AccountBalanceDecreasedEvent(
                this.getAccountId(),
                amount
        ));
    }

    @Override
    public void create() {
        publishEvent(new AccountCreatedEvent(
                this.getAccountId(),
                this.getUserId(),
                this.balance
        ));
    }
}
