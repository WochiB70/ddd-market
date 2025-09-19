package xyz.wochib70.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

@RequiredArgsConstructor
@Component
public class AccountFactory {

    private final AccountIdGenerator accountIdGenerator;

    public Account create(UserId userId, IdentifierId<Long> currencyId) {
        IdentifierId<Long> accountId = accountIdGenerator.nextAggregateId();
        AccountImpl account = new AccountImpl(accountId);
        account.setUserId(userId);
        account.setCurrencyId(currencyId);
        return account;
    }
}
