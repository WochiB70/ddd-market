package xyz.wochib70.web.mutation.task;

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
import xyz.wochib70.domain.task.cmd.ModifyTaskReceivedTaskExpireTimeCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "任务管理", description = "任务创建、修改、启用禁用等操作")
public class ModifyTaskReceivedTaskExpireTimeController {

    private final ModifyTaskReceivedTaskExpireTimeCmdHandler modifyTaskReceivedTaskExpireTimeCmdHandler;

    @PostMapping("/modify-received-task-expire-time")
    @Transactional
    @Operation(summary = "修改任务接收过期时间", description = "修改任务接收后的过期时间")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "任务不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyTaskReceivedTaskExpireTime(
            @Parameter(description = "修改任务接收过期时间请求参数", required = true)
            @RequestBody @Valid ModifyTaskReceivedTaskExpireTimeRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyTaskReceivedTaskExpireTimeCmdHandler.handle(request.toCmd());
    }
}