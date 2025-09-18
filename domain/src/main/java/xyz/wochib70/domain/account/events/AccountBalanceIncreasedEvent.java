package xyz.wochib70.domain.account.events;

import lombok.Getter;
import xyz.wochib70.domain.AbstractAggregateEvent;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.account.AccountImpl;

@Getter
public class AccountBalanceIncreasedEvent extends AbstractAggregateEvent<Long> {

    private final IdentifierId<Long> accountId;

    private final int increasedAmount;

    public AccountBalanceIncreasedEvent(
            IdentifierId<Long> accountId,
            int increasedAmount
    ) {
        super(AccountImpl.class, AccountBalanceIncreasedEvent.class);
        this.accountId = accountId;
        this.increasedAmount = increasedAmount;
    }
}
