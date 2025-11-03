package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public sealed interface Redeem extends Aggregate<Long, Long> permits RedeemImpl {

    IdentifierId<Long> getRedeemId();

    IdentifierId<Long> getActivityId();


    RedeemItemPrice getRedeemItemPriceOrThrow(IdentifierId<Long> redeemItemId);

    /**
     * 修改兑换项名称
     *
     * @param name 名称
     */
    void modifyRedeemName(String name);


    /**
     * 修改参与范围
     *
     * @param scope 参与范围
     */
    void modifyParticipateScope(RedeemParticipateScope scope);

    /**
     * 兑换
     *
     * @param redeemItemId 兑换项id
     * @param count        数量
     * @param userId       用户id
     */
    void redeem(IdentifierId<Long> redeemItemId, int count, UserId userId);

    /**
     * 添加兑换项
     *
     * @param info 兑换项信息
     * @throws DuplicatedRedeemItemNameException 兑换项名称已存在
     */
    IdentifierId<Long> addRedeemItem(RedeemItemInfo info);

    /**
     * 删除兑换项
     *
     * @param redeemItemId 兑换项id
     */
    void removeRedeemItem(IdentifierId<Long> redeemItemId);

    /**
     * 修改兑换项的基础信息
     *
     * @param redeemItemId 兑换项id
     * @param name         名称
     * @param description  描述
     */
    void modifyRedeemItemBasicInfo(IdentifierId<Long> redeemItemId, String name, String description);


    /**
     * 修改兑换项的价格
     *
     * @param redeemItemId 兑换项id
     * @param price        价格
     */
    void modifyRedeemItemPrice(IdentifierId<Long> redeemItemId, RedeemItemPrice price);

    /**
     * 修改兑换项的类型
     *
     * @param redeemItemId 兑换项id
     * @param type         类型
     */
    void modifyRedeemItemType(IdentifierId<Long> redeemItemId, RedeemItemType type);


}
