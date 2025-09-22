package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.utils.DurationUtil;

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


    public boolean inActiveTime() {
        return DurationUtil.validTimeWithDuration(LocalDateTime.now(), startTime, endTime);
    }
}
