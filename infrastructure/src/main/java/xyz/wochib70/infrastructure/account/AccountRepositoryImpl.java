package xyz.wochib70.infrastructure.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.account.Account;
import xyz.wochib70.domain.account.AccountFactory;
import xyz.wochib70.domain.account.AccountImpl;
import xyz.wochib70.domain.account.AccountRepository;

@AllArgsConstructor
@Component
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountDao accountDao;

    private final AccountFactory factory;

    @Override
    public Account queryAccountByCurrencyIdAndUserId(IdentifierId<Long> currencyId, UserId userId) {
        AccountEntity accountEntity = accountDao.queryAccountEntityByCurrencyIdAndUserId(currencyId.getId(), userId.getId())
                .orElseGet(() -> {
                    Account account = factory.create(userId, currencyId);
                    AccountEntity entity = toEntity(account);
                    return accountDao.save(entity);
                });
        return toDomain(accountEntity);
    }

    @Override
    public void update(Account account) {
        AccountEntity entity = toEntity(account);
        accountDao.updateById(entity);
    }

    private static AccountEntity toEntity(Account account) {
        AccountImpl impl = (AccountImpl) account;
        AccountEntity entity = new AccountEntity();
        entity.setId(impl.getAccountId().getId());
        entity.setUserId(impl.getUserId().getId());
        entity.setCurrencyId(impl.getCurrencyId().getId());
        entity.setBalance(impl.getBalance());
        return entity;
    }

    private static Account toDomain(AccountEntity entity) {
        AccountImpl account = new AccountImpl(new DefaultIdentifierId<>(entity.getId()));
        account.setUserId(new UserId(entity.getUserId()));
        account.setCurrencyId(new DefaultIdentifierId<>(entity.getCurrencyId()));
        account.setBalance(entity.getBalance());
        return account;
    }
}
