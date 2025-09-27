package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.Objects;

import static xyz.wochib70.domain.activity.ActivityDomainRegistry.activityRepository;

public record ActivityCountLimit(
        CountLimitType type,
        int count
) {


    public ActivityCountLimit {
        Objects.requireNonNull(type, "限制类型不能为空");
        if (count < 0) {
            throw new IllegalArgumentException("限制数量不能小于0");
        }
    }

    /**
     * 参与活动
     *
     * @throws ActivityLimitException 参与活动次数已达上限
     */
    public void participate(IdentifierId<Long> activityId, UserId userId) {
        Objects.requireNonNull(userId, "用户ID不能为空");
        switch (type) {
            case INFINITE -> {
            }
            case DAY_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInDay(activityId, userId)) {
                    throw new ActivityLimitException("今天参与活动次数已达上限，请明天在参与活动！");
                }
            }
            case THIS_WEEK_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInThisWeek(activityId, userId)) {
                    throw new ActivityLimitException("本周参与活动次数已达上限，请下周在参与活动！");
                }
            }
            case RECENTLY_WEEK_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInRecentlyWeek(activityId, userId)) {
                    throw new ActivityLimitException("最近一周参与活动次数已达上限，请下周在参与活动！");
                }
            }
            case THIS_MONTH_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInThisMonth(activityId, userId)) {
                    throw new ActivityLimitException("本月参与活动次数已达上限，请下月在参与活动！");
                }
            }
            case RECENTLY_MONTH_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInRecentlyMonth(activityId, userId)) {
                    throw new ActivityLimitException("最近一个月参与活动次数已达上限，请下月在参与活动！");
                }
            }
            case YEAR_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInYear(activityId, userId)) {
                    throw new ActivityLimitException("本年参与活动次数已达上限，请明年在参与活动！");
                }
            }
            case ACTIVITY_COUNT -> {
                if (count >= activityRepository().countParticipateActivityByUserIdInAllTime(activityId, userId)) {
                    throw new ActivityLimitException("参与活动次数已达上限，请勿再参与活动！");
                }
            }
        }
    }
}
