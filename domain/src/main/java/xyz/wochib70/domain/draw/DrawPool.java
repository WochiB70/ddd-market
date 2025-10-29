package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public sealed interface DrawPool extends Aggregate<Long, Long> permits DrawPoolImpl {


    IdentifierId<Long> getActivityId();

    IdentifierId<Long> getDrawPoolId();

    DrawPrice getDrawPrice();


    void modifyDrawPoolName(String name);

    /**
     * 抽奖
     *
     * @param userId 用户Id
     * @return 奖品信息
     */
    Reward draw(UserId userId);

    /**
     * 修改抽奖策略
     *
     * @param drawStrategyType 抽奖策略
     */
    void modifyDrawStrategy(DrawStrategyType drawStrategyType);

    /**
     * 修改抽奖价格
     *
     * @param drawPrice 抽奖价格
     */
    void modifyDrawPrice(DrawPrice drawPrice);

    /**
     * 修改参与范围
     *
     * @param scope 参与范围
     */
    void modifyParticipateScope(DrawPoolParticipateScope scope);

    /**
     * 修改奖品库存
     *
     * @param awardId   奖品Id
     * @param inventory 库存
     * @throws NoSuchAwardException 奖品不存在
     */
    void modifyDrawItemInventory(IdentifierId<Long> awardId, DrawItemInventory inventory);

    /**
     * 修改奖品信息
     *
     * @param awardId     奖品Id
     * @param name        奖品名称
     * @param description 奖品描述
     * @throws NoSuchAwardException     奖品不存在
     * @throws IllegalArgumentException name为null
     * @throws DuplicateAwardException  已存在同名name的奖品
     */
    void modifyDrawItemBasicInfo(IdentifierId<Long> awardId, String name, String description);

    /**
     * 修改奖品类型
     *
     * @param awardId 奖品Id
     * @param type    奖品类型
     * @throws IllegalArgumentException 奖品类型不能为空
     * @throws NoSuchAwardException     奖品不存在
     */
    void modifyDrawItemType(IdentifierId<Long> awardId, DrawItemType type);

    /**
     * 修改奖品权重
     *
     * @param awardId 奖品Id
     * @param weight  奖品权重
     * @throws IllegalArgumentException 权重不能小于0
     * @throws NoSuchAwardException     奖品不存在
     */
    void modifyDrawItemWeight(IdentifierId<Long> awardId, Integer weight);

    /**
     * 添加奖品
     *
     * @param drawItemInfo 奖品信息
     * @throws DuplicateAwardException 奖品已存在
     */
    IdentifierId<Long> addDrawItem(DrawItemInfo drawItemInfo);

    /**
     * 删除奖品
     *
     * @param awardId 奖品Id
     */
    void removeDrawItem(IdentifierId<Long> awardId);
}
