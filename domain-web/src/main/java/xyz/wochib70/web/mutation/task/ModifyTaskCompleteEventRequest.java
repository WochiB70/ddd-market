package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.task.cmd.ModifyTaskCompleteEventCmd;

@Data
@Schema(description = "修改任务完成事件请求")
public class ModifyTaskCompleteEventRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;
    
    @NotNull
    @Schema(description = "任务完成事件", example = "LOGIN")
    private CompleteEvent completeEvent;

    public ModifyTaskCompleteEventCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        return new ModifyTaskCompleteEventCmd(taskIdentifierId, completeEvent);
    }
}