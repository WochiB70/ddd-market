package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.cmd.ModifyTaskAwardCmd;
import xyz.wochib70.domain.task.TaskAward;
import xyz.wochib70.domain.task.TaskAwardType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改任务奖励请求")
public class ModifyTaskAwardRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;
    
    @NotNull
    @Schema(description = "任务奖励类型", example = "CURRENCY")
    private TaskAwardType taskAwardType;
    
    @NotNull
    @Schema(description = "奖励ID", example = "2001")
    private Long awardId;
    
    @NotNull
    @Schema(description = "奖励数量", example = "100")
    private Integer count;

    public ModifyTaskAwardCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        IdentifierId<Long> awardIdentifierId = new DefaultIdentifierId<>(awardId);
        TaskAward taskAward = new TaskAward(taskAwardType, awardIdentifierId, count);
        
        return new ModifyTaskAwardCmd(taskIdentifierId, taskAward);
    }
}