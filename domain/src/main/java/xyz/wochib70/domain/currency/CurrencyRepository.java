package xyz.wochib70.domain.currency;

import xyz.wochib70.domain.IdentifierId;

public interface CurrencyRepository {


    void save(Currency currency);

    /**
     * 通过Id查询货币聚合
     *
     * @param id 货币Id
     * @return 货币聚合
     * @throws java.util.NoSuchElementException 如果找不到该Id的货币聚合则抛出此异常
     */
    Currency findById(IdentifierId<Long> id);
}
