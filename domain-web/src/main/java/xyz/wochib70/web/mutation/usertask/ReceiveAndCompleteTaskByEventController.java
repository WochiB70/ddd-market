package xyz.wochib70.web.mutation.usertask;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.usertask.cmd.ReceiveAndCompleteTaskByEventCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/user-task")
@RequiredArgsConstructor
@Tag(name = "用户任务管理", description = "用户任务接收、完成等操作")
public class ReceiveAndCompleteTaskByEventController {

    private final ReceiveAndCompleteTaskByEventCmdHandler receiveAndCompleteTaskByEventCmdHandler;

    @PostMapping("/receive-and-complete-by-event")
    @Transactional
    @Operation(summary = "通过事件接收并完成任务", description = "通过触发事件自动接收并完成任务")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "任务接收并完成成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "任务不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void receiveAndCompleteTaskByEvent(
            @Parameter(description = "通过事件接收并完成任务请求参数", required = true)
            @RequestBody @Valid ReceiveAndCompleteTaskByEventRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        receiveAndCompleteTaskByEventCmdHandler.handle(request.toCmd());
    }
}