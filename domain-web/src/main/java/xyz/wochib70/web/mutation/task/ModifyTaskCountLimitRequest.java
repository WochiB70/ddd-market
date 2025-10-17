package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.cmd.ModifyTaskCountLimitCmd;
import xyz.wochib70.domain.task.TaskCountLimit;
import xyz.wochib70.domain.task.TaskCountLimitType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改任务计数限制请求")
public class ModifyTaskCountLimitRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;
    
    @NotNull
    @Schema(description = "任务计数限制类型", example = "LIMITED")
    private TaskCountLimitType taskCountLimitType;
    
    @Schema(description = "任务计数", example = "100")
    private Integer taskCount;

    public ModifyTaskCountLimitCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        TaskCountLimit countLimit = new TaskCountLimit(taskCountLimitType, taskCount);
        
        return new ModifyTaskCountLimitCmd(taskIdentifierId, countLimit);
    }
}