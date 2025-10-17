package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.*;
import xyz.wochib70.domain.task.cmd.CreateTaskCmd;

import java.time.LocalDateTime;

@Data
@Schema(description = "创建任务请求")
public class CreateTaskRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1001")
    private Long activityId;
    
    @NotNull
    @Schema(description = "任务名称", example = "每日签到任务")
    private String taskName;
    
    @NotNull
    @Schema(description = "任务描述", example = "用户每日签到可获得奖励")
    private String taskDescription;
    
    @NotNull
    @Schema(description = "任务计数限制类型", example = "UNLIMITED")
    private TaskCountLimitType taskCountLimitType;
    
    @Schema(description = "任务计数", example = "100")
    private Integer taskCount;
    
    @NotNull
    @Schema(description = "开始时间", example = "2025-01-01T00:00:00")
    private LocalDateTime startTime;
    
    @NotNull
    @Schema(description = "过期时间", example = "2025-12-31T23:59:59")
    private LocalDateTime expiredTime;
    
    @NotNull
    @Schema(description = "接收任务过期时间类型", example = "SECONDS")
    private ReceivedTaskExpireTimeType receivedTaskExpireTimeType;
    
    @Schema(description = "接收任务过期秒数", example = "86400")
    private Long receivedTaskExpireSeconds;

    public CreateTaskCmd toCmd() {
        IdentifierId<Long> activityIdentifierId = new DefaultIdentifierId<>(activityId);
        TaskInfo info = new TaskInfo(taskName, taskDescription);
        TaskCountLimit taskCountLimit = new TaskCountLimit(taskCountLimitType, taskCount);
        TaskDuration duration = new TaskDuration(startTime, expiredTime);
        ReceivedTaskExpireTime receivedTaskExpireTime = new ReceivedTaskExpireTime(receivedTaskExpireTimeType, receivedTaskExpireSeconds);
        
        return new CreateTaskCmd(activityIdentifierId, info, taskCountLimit, duration, receivedTaskExpireTime);
    }
}