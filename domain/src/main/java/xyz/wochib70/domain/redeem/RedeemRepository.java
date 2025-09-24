package xyz.wochib70.domain.redeem;

import xyz.wochib70.domain.IdentifierId;

import java.util.List;

public interface RedeemRepository {


    /**
     * 根据ID查询兑换池
     *
     * @param redeemId 兑换池Id
     * @return 兑换池聚合根
     * @throws NoSuchRedeemExistsException 如果没找到
     */
    Redeem findByIdOrThrow(IdentifierId<Long> redeemId);


    /**
     * 保存兑换池
     *
     * @param redeem 兑换池聚合根
     */
    void save(Redeem redeem);

    /**
     * 删除兑换池
     *
     * @param redeem 兑换池聚合根
     */
    void delete(Redeem redeem);

    /**
     * 更新兑换池
     *
     * @param redeem 兑换池聚合根
     */
    void update(Redeem redeem);

    /**
     * 根据活动Id查询兑换池
     *
     * @param activityId 活动Id
     * @return empty list 如果没找到
     */
    List<Redeem> findByActivityId(IdentifierId<Long> activityId);
}
