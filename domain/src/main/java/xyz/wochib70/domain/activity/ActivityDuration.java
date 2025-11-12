package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.utils.DurationUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public record ActivityDuration(
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public ActivityDuration {
        if (Objects.isNull(startTime)) {
            throw new IllegalArgumentException("活动开始时间不能为空");
        }

        if (Objects.isNull(endTime)) {
            throw new IllegalArgumentException("活动结束时间不能为空");
        }

        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            throw new IllegalArgumentException("活动开始时间不能大于等于结束时间");
        }
    }


    public boolean inActiveTime() {
        return DurationUtil.validTimeWithDuration(LocalDateTime.now(), startTime, endTime);
    }
}
