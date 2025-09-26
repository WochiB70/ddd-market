package xyz.wochib70.domain.task;

import xyz.wochib70.domain.IdentifierId;

import java.util.Objects;

public record TaskAward(
        TaskAwardType type,
        IdentifierId<Long> awardId,
        Integer count
) {
    public TaskAward {
        Objects.requireNonNull(type, "任务完成奖励类型不能为null");
        Objects.requireNonNull(awardId, "任务完成奖励Id不能为null");
        Objects.requireNonNull(count, "任务完成奖励数量不能为null");
    }
}
