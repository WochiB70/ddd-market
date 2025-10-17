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
import xyz.wochib70.domain.credential.cmd.InvalidCredentialCmdHandler;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Tag(name = "凭证管理", description = "凭证创建、修改、验证等操作")
public class InvalidCredentialController {

    private final InvalidCredentialCmdHandler invalidCredentialCmdHandler;

    @PostMapping("/invalid")
    @Transactional
    @Operation(summary = "使凭证失效", description = "将指定凭证标记为失效状态，使其无法再被使用")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "凭证失效操作成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "凭证不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void invalidCredential(
            @Parameter(description = "使凭证失效的请求参数", required = true)
            @RequestBody @Valid InvalidCredentialRequest request
    ) {
        invalidCredentialCmdHandler.handle(request.toCmd());
    }
}