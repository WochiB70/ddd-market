package xyz.wochib70.web.mutation.usertask;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.usertask.cmd.CompleteUserTaskCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "完成用户任务请求")
public class CompleteUserTaskRequest {

    @NotNull
    @Schema(description = "用户任务ID", example = "12345")
    private Long userTaskId;
    
    @NotNull
    @Schema(description = "用户ID", example = "1001")
    private Long userId;

    public CompleteUserTaskCmd toCmd() {
        IdentifierId<Long> userTaskIdentifierId = new DefaultIdentifierId<>(userTaskId);
        UserId userIdObj = new UserId(userId);
        return new CompleteUserTaskCmd(userTaskIdentifierId, userIdObj);
    }
}