package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.TaskInfo;
import xyz.wochib70.domain.task.cmd.ModifyTaskInfoCmd;

@Data
@Schema(description = "修改任务信息请求")
public class ModifyTaskInfoRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;
    
    @NotNull
    @Schema(description = "任务名称", example = "每日签到任务")
    private String taskName;
    
    @NotNull
    @Schema(description = "任务描述", example = "用户每日签到可获得奖励")
    private String taskDescription;

    public ModifyTaskInfoCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        TaskInfo info = new TaskInfo(taskName, taskDescription);
        
        return new ModifyTaskInfoCmd(taskIdentifierId, info);
    }
}