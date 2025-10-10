package xyz.wochib70.domain.task;

import xyz.wochib70.domain.activity.Activity;

import java.time.LocalDateTime;
import java.util.Objects;

public record ReceivedTaskExpireTime(
        ReceivedTaskExpireTimeType type,
        Long seconds
) {

    public ReceivedTaskExpireTime {
        if (type == null) {
            throw new IllegalArgumentException("任务过期类型不能为空");
        }
        if (Objects.equals(type, ReceivedTaskExpireTimeType.EXPIRE_IN_TIME) && (seconds == null || seconds <= 0)) {
            throw new IllegalArgumentException("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0");
        }
    }

    public LocalDateTime calculate(Activity activity) {
        return switch (type) {
            case EXPIRE_IN_TIME -> LocalDateTime.now().plusSeconds(seconds);
            case EXPIRE_THIS_ACTIVITY_END -> {
                Objects.requireNonNull(activity, "当前结束类型与Activity相关， Activity不能为null");
                yield activity.getDuration().endTime();
            }
            case EXPIRE_TODAY_END -> LocalDateTime.now()
                    .plusDays(1)
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            case EXPIRE_THIS_WEEK_END -> {
                LocalDateTime now = LocalDateTime.now();
                yield now.plusDays((7 - now.getDayOfWeek().getValue()))
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0);
            }
            case null -> throw new IllegalArgumentException("非法的expire time type");
        };
    }

}

