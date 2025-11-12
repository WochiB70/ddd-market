package xyz.wochib70.domain.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class DurationUtil {


    public static final LocalDateTime MIN_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

    public static final LocalDateTime MAX_TIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private DurationUtil() {
        throw new UnsupportedOperationException("这是一个工具类禁止实例化");
    }

    /**
     * 检测time是否在startTime和endTime之间，不含端点时间
     *
     * @param time      时间点
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true  在活动时间段内; false  不在活动时间段内
     * @throws IllegalArgumentException 任意参数为空
     */
    public static boolean validTimeWithDuration(LocalDateTime time,
                                                LocalDateTime startTime,
                                                LocalDateTime endTime) {
        Objects.requireNonNull(time, "检测的时间点不能为空");
        Objects.requireNonNull(startTime, "检测的时间点不能为空");
        Objects.requireNonNull(endTime, "检测的时间点不能为空");
        return startTime.isBefore(time) && endTime.isAfter(time);
    }


    /**
     * 获取两个时间中的最小时间
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 最小时间
     * @throws IllegalArgumentException 任意参数为空
     */
    public static LocalDateTime min(LocalDateTime time1, LocalDateTime time2) {
        ParameterUtil.requireNonNull(time1, "时间1不能为空");
        ParameterUtil.requireNonNull(time2, "时间2不能为空");
        return time1.isBefore(time2) ? time1 : time2;
    }

    /**
     * 获取两个时间中的最大时间
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 最大时间
     * @throws IllegalArgumentException 任意参数为空
     */
    public static LocalDateTime max(LocalDateTime time1, LocalDateTime time2) {
        ParameterUtil.requireNonNull(time1, "时间1不能为空");
        ParameterUtil.requireNonNull(time2, "时间2不能为空");
        return time1.isAfter(time2) ? time1 : time2;
    }
}
