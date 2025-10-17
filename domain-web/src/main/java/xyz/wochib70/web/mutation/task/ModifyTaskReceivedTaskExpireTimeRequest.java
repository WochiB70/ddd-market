package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.cmd.ModifyTaskReceivedTaskExpireTimeCmd;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;
import xyz.wochib70.domain.task.ReceivedTaskExpireTimeType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改任务接收过期时间请求")
public class ModifyTaskReceivedTaskExpireTimeRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;
    
    @NotNull
    @Schema(description = "接收任务过期时间类型", example = "SECONDS")
    private ReceivedTaskExpireTimeType receivedTaskExpireTimeType;
    
    @Schema(description = "接收任务过期秒数", example = "86400")
    private Long receivedTaskExpireSeconds;

    public ModifyTaskReceivedTaskExpireTimeCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        ReceivedTaskExpireTime receivedTaskExpireTime = new ReceivedTaskExpireTime(receivedTaskExpireTimeType, receivedTaskExpireSeconds);
        
        return new ModifyTaskReceivedTaskExpireTimeCmd(taskIdentifierId, receivedTaskExpireTime);
    }
}