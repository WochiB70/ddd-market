package xyz.wochib70.domain.task;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.events.*;

import java.util.Objects;

@Getter
@Setter
public non-sealed class TaskImpl extends AbstractAggregate<Long> implements Task {

    private IdentifierId<Long> activityId;

    private TaskInfo info;

    private TaskStatus status;

    private TaskCountLimit taskCountLimit;

    private TaskDuration duration;

    private CompleteEvent completeEvent;

    private ReceivedTaskExpireTime receivedTaskExpireTime;

    private TaskAward taskAward;

    public TaskImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getTaskId() {
        return identifierId();
    }

    @Override
    public void receive(UserId userId) {
        if (userId == null) {
            throw new IllegalArgumentException("任务已过期");
        }
        if (!duration.valid()) {
            throw new TaskCountLimitException("任务已过期");
        }
        if (!Objects.equals(status, TaskStatus.ENABLE)) {
            throw new TaskCannotClaimedException("任务不能被领取");
        }
        taskCountLimit.receiveTask(userId, getTaskId());
        publishEvent(new TaskReceivedEvent(
                getTaskId(),
                userId
        ));
    }

    @Override
    public void disable() {
        if (!Objects.equals(status, TaskStatus.UNABLE)) {
            this.status = TaskStatus.UNABLE;
            publishEvent(new TaskDisabledEvent(
                    getTaskId()
            ));
        }
    }

    @Override
    public void enable() {
        if (!Objects.equals(status, TaskStatus.ENABLE)) {
            this.status = TaskStatus.ENABLE;
            publishEvent(new TaskEnabledEvent(
                    getTaskId()
            ));
        }
    }

    @Override
    public void modifyInfo(TaskInfo info) {
        if (info == null) {
            throw new IllegalArgumentException("任务信息不能为空");
        }
        if (!Objects.equals(this.info, info)) {
            this.info = info;
            publishEvent(new TaskInfoModifiedEvent(
                    getTaskId(),
                    info
            ));
        }
    }

    @Override
    public void modifyCompleteEvent(CompleteEvent completeEvent) {
        if (completeEvent == null) {
            throw new IllegalArgumentException("任务完成事件不能为空");
        }
        if (!Objects.equals(this.completeEvent, completeEvent)) {
            this.completeEvent = completeEvent;
            publishEvent(new TaskCompleteEventModifiedEvent(
                    getTaskId(),
                    completeEvent
            ));
        }
    }

    @Override
    public void modifyCountLimit(TaskCountLimit taskCountLimit) {
        if (taskCountLimit == null) {
            throw new IllegalArgumentException("任务限制不能为空");
        }
        if (!Objects.equals(this.taskCountLimit, taskCountLimit)) {
            this.taskCountLimit = taskCountLimit;
            publishEvent(new TaskCountLimitModifiedEvent(
                    getTaskId(),
                    taskCountLimit
            ));
        }
    }

    @Override
    public void modifyReceivedTaskExpireTime(ReceivedTaskExpireTime receivedTaskExpireTime) {
        if (receivedTaskExpireTime == null) {
            throw new IllegalArgumentException("任务领取的过期时间不能为空");
        }
        if (!Objects.equals(this.receivedTaskExpireTime, receivedTaskExpireTime)) {
            this.receivedTaskExpireTime = receivedTaskExpireTime;
            publishEvent(new TaskReceivedTaskExpireTimeModifiedEvent(
                    getActivityId(),
                    receivedTaskExpireTime
            ));
        }
    }

    @Override
    public void modifyTaskAward(TaskAward taskAward) {
        Objects.requireNonNull(taskAward, "任务奖励不能为空");
        if (!Objects.equals(this.taskAward, taskAward)) {
            this.taskAward = taskAward;
            publishEvent(new TaskAwardModifiedEvent(
                    getTaskId(),
                    taskAward
            ));
        }
    }

    @Override
    public void create() {
        publishEvent(new TaskCreatedEvent(
                this
        ));
    }
}
