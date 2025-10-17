package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.cmd.EnableTaskCmd;

@Data
@Schema(description = "启用任务请求")
public class EnableTaskRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;

    public EnableTaskCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        return new EnableTaskCmd(taskIdentifierId);
    }
}