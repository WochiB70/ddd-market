package xyz.wochib70.domain.task;

import java.util.Objects;

public record ReceivedTaskExpireTime(
        ReceivedTaskExpireTimeType type,
        Long seconds
) {

    public ReceivedTaskExpireTime {
        if (type == null) {
            throw new IllegalArgumentException("任务过期类型不能为空");
        }
        if (!Objects.equals(type, ReceivedTaskExpireTimeType.EXPIRE_IN_TIME) && (seconds == null || seconds <= 0)) {
            throw new IllegalArgumentException("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0");
        }
    }
}

