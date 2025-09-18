package xyz.wochib70.domain.account.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.account.AccountImpl;

@Getter
public class AccountBalanceDecreasedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> accountId;

    private final int decreasedAmount;

    public AccountBalanceDecreasedEvent(
            IdentifierId<Long> accountId,
            int decreasedAmount
    ) {
        super(AccountImpl.class, AccountBalanceDecreasedEvent.class);
        this.accountId = accountId;
        this.decreasedAmount = decreasedAmount;
    }
}
