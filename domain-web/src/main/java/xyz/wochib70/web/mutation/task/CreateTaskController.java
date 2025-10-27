package xyz.wochib70.web.mutation.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.task.cmd.CreateTaskCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "任务管理", description = "任务创建、修改、启用禁用等操作")
public class CreateTaskController {

    private final CreateTaskCmdHandler createTaskCmdHandler;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "创建任务", description = "创建一个新的任务")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "任务创建成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createTask(
            @Parameter(description = "创建任务请求参数", required = true)
            @RequestBody @Valid CreateTaskRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        return createTaskCmdHandler.handle(request.toCmd());
    }
}