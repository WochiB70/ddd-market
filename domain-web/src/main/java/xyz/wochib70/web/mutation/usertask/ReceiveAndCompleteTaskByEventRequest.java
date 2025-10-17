package xyz.wochib70.web.mutation.usertask;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.CompleteEvent;
import xyz.wochib70.domain.usertask.cmd.ReceiveAndCompleteTaskByEventCmd;

@Data
@Schema(description = "通过事件接收并完成任务请求")
public class ReceiveAndCompleteTaskByEventRequest {

    @NotNull
    @Schema(description = "用户ID", example = "1001")
    private Long userId;
    
    @NotNull
    @Schema(description = "完成事件", example = "COMPLETE_EVENT")
    private CompleteEvent completeEvent;

    public ReceiveAndCompleteTaskByEventCmd toCmd() {
        UserId userIdObj = new UserId(userId);
        return new ReceiveAndCompleteTaskByEventCmd(userIdObj, completeEvent);
    }
}