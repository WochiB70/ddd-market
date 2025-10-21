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
import xyz.wochib70.domain.credential.cmd.ValidCredentialCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Tag(name = "凭证管理", description = "凭证创建、修改、验证等操作")
public class ValidCredentialController {

    private final ValidCredentialCmdHandler validCredentialCmdHandler;

    @PostMapping("/valid")
    @Transactional
    @Operation(summary = "验证凭证", description = "验证指定凭证的有效性，包括凭证状态、有效期和剩余使用次数")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "凭证验证成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "凭证不存在"),
        @ApiResponse(responseCode = "422", description = "凭证已过期或使用次数不足"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void validCredential(
            @Parameter(description = "验证凭证的请求参数", required = true)
            @RequestBody @Valid ValidCredentialRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        validCredentialCmdHandler.handle(request.toCmd());
    }
}