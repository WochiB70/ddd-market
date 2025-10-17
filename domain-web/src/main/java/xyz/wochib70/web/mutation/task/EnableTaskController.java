package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.task.cmd.EnableTaskCmdHandler;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "任务管理", description = "任务创建、修改、启用禁用等操作")
public class EnableTaskController {

    private final EnableTaskCmdHandler enableTaskCmdHandler;

    @PostMapping("/enable")
    @Operation(summary = "启用任务", description = "启用指定的任务")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "启用成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "任务不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void enableTask(
            @Parameter(description = "启用任务请求参数", required = true)
            @RequestBody @Valid EnableTaskRequest request
    ) {
        enableTaskCmdHandler.handle(request.toCmd());
    }
}