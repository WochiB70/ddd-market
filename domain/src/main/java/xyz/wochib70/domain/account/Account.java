package xyz.wochib70.domain.account;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;

public sealed interface Account extends Aggregate<Long, Long> permits AccountImpl {


    IdentifierId<Long> getAccountId();

    IdentifierId<Long> getUserId();

    /**
     * 增加余额
     *
     * @param amount 金额
     * @throws IllegalArgumentException 金额小于等于0时
     */
    void increateBalance(int amount);

    /**
     * 减少余额
     *
     * @param amount 金额
     * @throws InsufficientBalanceException 余额不足时
     * @throws IllegalArgumentException     金额大于于等于0时
     */
    void decreaseBalance(int amount);


}
