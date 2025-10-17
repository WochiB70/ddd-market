package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.task.cmd.ModifyTaskCompleteEventCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "任务管理", description = "任务创建、修改、启用禁用等操作")
public class ModifyTaskCompleteEventController {

    private final ModifyTaskCompleteEventCmdHandler modifyTaskCompleteEventCmdHandler;

    @PostMapping("/modify-complete-event")
    @Operation(summary = "修改任务完成事件", description = "修改任务的完成事件条件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "任务不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyTaskCompleteEvent(
            @Parameter(description = "修改任务完成事件请求参数", required = true)
            @RequestBody @Valid ModifyTaskCompleteEventRequest request
    ) {
        modifyTaskCompleteEventCmdHandler.handle(request.toCmd());
    }
}