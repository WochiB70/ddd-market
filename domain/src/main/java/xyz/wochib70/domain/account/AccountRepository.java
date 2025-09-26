package xyz.wochib70.domain.account;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public interface AccountRepository {
    /**
     * 根据货币Id和用户Id查询账户
     *
     * @param currencyId 货币Id
     * @param userId     用户Id
     * @return Account 如果数据库不存在则需要创建一个账户
     */
    Account queryAccountByCurrencyIdAndUserId(IdentifierId<Long> currencyId, UserId userId);


    /**
     * 保存账户
     *
     * @param account 账户
     */
    void update(Account account);
}
