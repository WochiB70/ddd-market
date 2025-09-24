package xyz.wochib70.domain.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class DurationUtil {


    private DurationUtil() {
        throw new UnsupportedOperationException("这是一个工具类禁止实例化");
    }

    /**
     * startTime   endTime
     * null        null     永久生效
     * null        not null      endTime之前有效
     * not null    null      startTime之后有效
     * not null    not null      startTime和endTime之间有效
     *
     * @param time      时间点
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true  在活动时间段内; false  不在活动时间段内
     */
    public static boolean validTimeWithDuration(LocalDateTime time,
                                                LocalDateTime startTime,
                                                LocalDateTime endTime) {
        Objects.requireNonNull(time, "检测的时间点不能为null");
        if (Objects.isNull(startTime) && Objects.isNull(endTime)) {
            return true;
        }
        if (Objects.isNull(startTime)) {
            return endTime.isAfter(time);
        }
        if (Objects.isNull(endTime)) {
            return startTime.isBefore(time);
        }

        return startTime.isBefore(time) && endTime.isAfter(time);
    }
}
