package xyz.wochib70.web.mutation.activity;

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
import xyz.wochib70.domain.activity.cmd.ModifyActivityCredentialLimitCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class ModifyActivityCredentialLimitController {

    private final ModifyActivityCredentialLimitCmdHandler modifyActivityCredentialLimitCmdHandler;

    @PostMapping("/modify-credential-limit")
    @Transactional
    @Operation(summary = "修改活动凭证限制", description = "修改指定活动的凭证使用限制配置")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "修改成功"),
            @ApiResponse(responseCode = "400", description = "请求参数无效"),
            @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    public void modifyActivityCredentialLimit(
            @Parameter(description = "修改活动凭证限制请求参数", required = true)
            @RequestBody @Valid ModifyActivityCredentialLimitRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyActivityCredentialLimitCmdHandler.handle(request.toCmd());
    }
}