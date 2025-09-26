package xyz.wochib70.domain.task;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.List;

public interface TaskRepository {

    /**
     * 按照Id查询任务
     *
     * @param taskId 任务id
     * @return 任务
     * @throws NoSuchTaskException 没有该任务
     */
    Task queryTaskByIdOrThrow(IdentifierId<Long> taskId);

    void save(Task task);


    /**
     * 统计用户今天领取了多少任务
     *
     * @param userId 用户id
     * @param taskId 任务id
     * @return 领取数量
     */
    Integer countReceivedTaskInDay(UserId userId, IdentifierId<Long> taskId);


    /**
     * 统计用户在任务生效时间段内领取了多少任务
     *
     * @param userId 用户id
     * @param taskId 任务id
     * @return 领取数量
     */
    Integer countReceivedTaskByUserIdInDuration(UserId userId, IdentifierId<Long> taskId);

    /**
     * 按照完成条件查询可领取的任务
     *
     * @param completeEvent 完成条件
     */
    List<Task> queryReceivableTaskByCompleteEvent(CompleteEvent completeEvent);

    /**
     * 按照活动查询任务
     *
     * @param activityId 活动id
     * @return empty list 如果不存在
     */
    List<Task> queryTaskByActivityId(IdentifierId<Long> activityId);
}
