package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.task.cmd.DisableTaskCmd;

@Data
@Schema(description = "禁用任务请求")
public class DisableTaskRequest {

    @NotNull
    @Schema(description = "任务ID", example = "1001")
    private Long taskId;

    public DisableTaskCmd toCmd() {
        IdentifierId<Long> taskIdentifierId = new DefaultIdentifierId<>(taskId);
        return new DisableTaskCmd(taskIdentifierId);
    }
}