package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.Optional;

public interface ActivityRepository {

    /**
     * 获取用户今天在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInDay(IdentifierId<Long> activityId, UserId userId);

    /**
     * 获取用户本周在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInThisWeek(IdentifierId<Long> activityId, UserId userId);

    /**
     * 获取用户最近一周在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInRecentlyWeek(IdentifierId<Long> activityId, UserId userId);

    /**
     * 获取用户本月在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInThisMonth(IdentifierId<Long> activityId, UserId userId);

    /**
     * 获取用户最近一月在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInRecentlyMonth(IdentifierId<Long> activityId, UserId userId);

    /**
     * 获取用户本年在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInYear(IdentifierId<Long> activityId, UserId userId);


    /**
     * 获取用户所有时间在活动参与次数
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return 0 表示没有参与
     */
    Integer countParticipateActivityByUserIdInAllTime(IdentifierId<Long> activityId, UserId userId);

    /**
     * 保存活动
     *
     * @param activity 活动
     */
    void save(Activity activity);

    /**
     * 查询活动
     *
     * @param activityId 活动id
     * @throws ActivityNotFoundException 活动不存在
     */
    Activity queryActivityByIdOrThrow(IdentifierId<Long> activityId);

    /**
     * 更新活动
     *
     * @param activity 活动
     */
    void update(Activity activity);

    /**
     * 查询活动
     *
     * @param activityId 活动Id
     * @return Optional.empty() 活动不存在
     */
    Optional<Activity> queryActivityById(IdentifierId<Long> activityId);
}
