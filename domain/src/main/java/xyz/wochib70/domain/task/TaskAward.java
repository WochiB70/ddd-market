package xyz.wochib70.domain.task;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

public record TaskAward(
        TaskAwardType type,
        IdentifierId<Long> awardId,
        Integer count
) {
    public TaskAward {
        ParameterUtil.requireNonNull(type, "任务完成奖励类型不能为null");
        ParameterUtil.requireNonNull(awardId, "任务完成奖励Id不能为null");
        ParameterUtil.requireNonNull(count, "任务完成奖励数量不能为null");
    }
}
