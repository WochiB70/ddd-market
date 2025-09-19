package xyz.wochib70.domain.activity;

import java.time.LocalDateTime;
import java.util.Objects;

public record ActivityDuration(
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public ActivityDuration {
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(startTime) && Objects.nonNull(endTime) && endTime.isBefore(now)) {
            throw new IllegalArgumentException("当不存在startTime的时候，endTime的事件不能在当前时间之前");
        }

        if (Objects.nonNull(startTime) && Objects.nonNull(endTime) && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("开始时间不能大于结束时间");
        }
    }

    /**
     * startTime   endTime
     * null        null     永久生效
     * null        not null      endTime之前有效
     * not null    null      startTime之后有效
     * not null    not null      startTime和endTime之间有效
     *
     * @return true  在活动时间段内; false  不在活动时间段内
     */
    public boolean inActiveTime() {
        if (Objects.isNull(startTime) && Objects.isNull(endTime)) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(startTime) && endTime.isAfter(now)) {
            return true;
        }
        if (Objects.isNull(endTime) && startTime.isBefore(now)) {
            return true;
        }

        return startTime.isBefore(now) && endTime.isAfter(now);

    }
}
