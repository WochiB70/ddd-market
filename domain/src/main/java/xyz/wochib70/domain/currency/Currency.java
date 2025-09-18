package xyz.wochib70.domain.currency;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;

/**
 * @author WochiB70
 */
public sealed interface Currency extends Aggregate<Long, Long> permits CurrencyImpl {

    IdentifierId<Long> getCurrencyId();

    /**
     * 修改币种信息
     *
     * @param info 币种信息
     */
    void modifyInfo(CurrencyInfo info);

    /**
     * 开启币种
     */
    void enable();

    /**
     * 关闭币种
     */
    void deactivate();

    /**
     * 币种是否启用
     *
     * @return true - 启用； false - 关闭
     */
    boolean isEnabled();

    /**
     * 增加币种引用计数
     */
    void increaseReferenceCount();

    /**
     * 减少币种引用计数
     */
    void decreaseReferenceCount();
}
