package xyz.wochib70.domain.task;

import lombok.extern.slf4j.Slf4j;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.utils.DurationUtil;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
public record ReceivedTaskExpireTime(
        ReceivedTaskExpireTimeType type,
        Long seconds
) {

    public ReceivedTaskExpireTime {
        ParameterUtil.requireNonNull(type, "任务过期类型不能为空");
        if (Objects.equals(type, ReceivedTaskExpireTimeType.EXPIRE_IN_TIME) && (seconds == null || seconds <= 0)) {
            throw new IllegalArgumentException("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0");
        }
    }

    public LocalDateTime calculate(Activity activity) {
        return switch (type) {
            case EXPIRE_IN_TIME -> LocalDateTime.now().plusSeconds(seconds);
            case EXPIRE_THIS_ACTIVITY_END -> {
                ParameterUtil.requireNonNull(activity, "当前结束类型与Activity相关， Activity不能为null");
                yield activity.getDuration().endTime();
            }
            case EXPIRE_TODAY_END -> {
                LocalDateTime time = LocalDateTime.now()
                        .plusDays(1)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0);
                if (activity != null && activity.getDuration().endTime() != null) {
                    yield DurationUtil.min(time, activity.getDuration().endTime());
                }
                yield time;
            }
            case EXPIRE_THIS_WEEK_END -> {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime time = now.plusDays((7 - now.getDayOfWeek().getValue()))
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0);
                if (activity != null && activity.getDuration().endTime() != null) {
                    log.info("当前结束类型为本周结束，且配置了Activity，取本周结束和Activity结束的最小值作为结束事件");
                    yield DurationUtil.min(time, activity.getDuration().endTime());
                }
                yield time;
            }
            case null -> throw new IllegalArgumentException("非法的expire time type");
        };
    }

}

