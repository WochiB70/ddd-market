package xyz.wochib70.domain.usertask;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.Optional;

public interface UserTaskRepository {

    /**
     * 查询用户未完成的任务
     *
     * @param userTaskId 用户任务id
     * @param userId     用户id
     * @return 用户任务
     * @throws NoSuchUserTaskException 用户任务不存在
     */
    UserTask queryUncompletedUserTaskByIdAndUserIdOrThrow(IdentifierId<Long> userTaskId, UserId userId);


    /**
     * 保存用户任务
     *
     * @param userTask 用户任务
     */
    void save(UserTask userTask);

    /**
     * 查询用户指定任务Id的未完成的用户任务
     *
     * @param userId 用户id
     * @param taskId 任务id
     * @return Optional.empty() 如果没有找到
     */
    Optional<UserTask> queryUncompletedUserTaskByUserIdAndTaskId(UserId userId, IdentifierId<Long> taskId);

    /**
     * 查询用户任务
     *
     * @param userTaskId 用户任务id
     * @return 用户任务
     * @throws NoSuchUserTaskException 用户任务不存在
     */
    UserTask queryUserTaskByIdOrThrow(IdentifierId<Long> userTaskId);
}
