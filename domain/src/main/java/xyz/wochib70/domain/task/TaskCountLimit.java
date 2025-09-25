package xyz.wochib70.domain.task;


import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.Objects;

import static xyz.wochib70.domain.task.TaskDomainRegistry.taskRepository;

public record TaskCountLimit(
        CountLimitType type,
        Integer count
) {
    public TaskCountLimit {
        if (type == null) {
            throw new IllegalArgumentException("任务限制类型不能为空");
        }
        if (!Objects.equals(type, CountLimitType.INFINITE) && (count == null || count <= 0)) {
            throw new IllegalArgumentException("当前类型不是无限制，次数不能小于等于0");
        }
    }

    public void receiveTask(UserId userId, IdentifierId<Long> taskId) {
        switch (type) {
            case INFINITE -> {
                // 无限制
            }
            case DAY_COUNT -> {
                Integer receivedCount = taskRepository().countReceivedTaskInDay(userId, taskId);
                if (receivedCount >= count) {
                    throw new TaskCountLimitException("今日任务已超过限制");
                }
            }
            case TASK_DURATION -> {
                Integer receivedCount = taskRepository().countReceivedTaskByUserIdInDuration(userId, taskId);
                if (receivedCount >= count) {
                    throw new TaskCountLimitException("任务已超过限制");
                }
            }
            default -> throw new IllegalArgumentException("未知的任务限制类型");
        }
    }
}
