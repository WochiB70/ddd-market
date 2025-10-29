package xyz.wochib70.domain.task;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public sealed interface Task extends Aggregate<Long, Long> permits TaskImpl {

    IdentifierId<Long> getTaskId();

    IdentifierId<Long> getActivityId();

    ReceivedTaskExpireTime getReceivedTaskExpireTime();

    CompleteEvent getCompleteEvent();


    TaskAward getTaskAward();

    TaskDuration getDuration();

    /**
     * 接收任务
     *
     * @param userId 用户id
     * @throws TaskCountLimitException    任务已超过限制
     * @throws TaskCannotClaimedException 任务不能被领取
     */
    void receive(UserId userId);

    void disable();

    void enable();

    /**
     * 修改任务信息
     *
     * @param info 任务信息
     */
    void modifyInfo(TaskInfo info);

    /**
     * 修改完成事件
     *
     * @param completeEvent 完成事件
     */
    void modifyCompleteEvent(CompleteEvent completeEvent);

    /**
     * 修改任务限制
     *
     * @param taskCountLimit 任务限制
     */
    void modifyCountLimit(TaskCountLimit taskCountLimit);


    /**
     * 更新领取任务的过期时间
     *
     * @param receivedTaskExpireTime 领取任务的过期时间
     */
    void modifyReceivedTaskExpireTime(ReceivedTaskExpireTime receivedTaskExpireTime);


    /**
     * 修改任务奖品
     *
     * @param taskAward 奖品
     */
    void modifyTaskAward(TaskAward taskAward);

    /**
     * 修改任务时间
     *
     * @param taskDuration 时间
     */
    void modifyTaskDuration(TaskDuration taskDuration);
}
