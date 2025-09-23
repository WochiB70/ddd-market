package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;

import java.util.List;

public interface DrawPoolRepository {

    /**
     * 查询抽奖池
     *
     * @param drawPoolId 抽奖池ID
     * @return 抽奖池
     * @throws NoSuchDrawPoolException 抽奖池不存在
     */
    DrawPool findByIdOrThrow(IdentifierId<Long> drawPoolId);

    void save(DrawPool drawPool);

    void update(DrawPool drawPool);

    /**
     * 根据活动ID查询抽奖池
     *
     * @param activityId 活动ID
     * @return empty 如果未找到
     */
    List<DrawPool> findByActivityId(IdentifierId<Long> activityId);

    /**
     * 删除抽奖池
     *
     * @param drawPool 抽奖池
     */
    void delete(DrawPool drawPool);
}
