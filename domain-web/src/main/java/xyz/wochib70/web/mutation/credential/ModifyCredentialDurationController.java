package xyz.wochib70.web.mutation.credential;

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
import xyz.wochib70.domain.credential.cmd.ModifyCredentialDurationCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Tag(name = "凭证管理", description = "凭证创建、修改、验证等操作")
public class ModifyCredentialDurationController {

    private final ModifyCredentialDurationCmdHandler modifyCredentialDurationCmdHandler;

    @PostMapping("/modify-duration")
    @Transactional
    @Operation(summary = "修改凭证有效期", description = "修改指定凭证的有效期时间，延长或缩短凭证的使用期限")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "凭证有效期修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效或新的有效期时间不合法"),
        @ApiResponse(responseCode = "404", description = "凭证不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyCredentialDuration(
            @Parameter(description = "修改凭证有效期的请求参数", required = true)
            @RequestBody @Valid ModifyCredentialDurationRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyCredentialDurationCmdHandler.handle(request.toCmd());
    }
}