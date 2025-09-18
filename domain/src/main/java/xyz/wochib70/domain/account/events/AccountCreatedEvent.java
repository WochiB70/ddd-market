package xyz.wochib70.domain.account.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.account.AccountImpl;

@Getter
public class AccountCreatedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> accountId;

    private final IdentifierId<Long> userId;

    private final int balance;

    public AccountCreatedEvent(
            IdentifierId<Long> accountId,
            IdentifierId<Long> userId,
            int balance
    ) {
        super(AccountImpl.class, AccountCreatedEvent.class);
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }
}
