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


    /**
     * 获取两个时间中的最小时间
     * <p>
     * 当两个值都为null时返回null
     * 当两个值有一个为null时返回另一个值
     * 当两个值都不为null时返回较小的值
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 最小时间
     */
    public static LocalDateTime min(LocalDateTime time1, LocalDateTime time2) {
        if (Objects.isNull(time1) && Objects.isNull(time2)) {
            return null;
        }
        if (Objects.isNull(time1)) {
            return time2;
        }
        if (Objects.isNull(time2)) {
            return time1;
        }
        return time1.isBefore(time2) ? time1 : time2;
    }
}
