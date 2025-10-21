package xyz.wochib70.web.mutation.credential;

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
import xyz.wochib70.domain.credential.cmd.CreateCredentialCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Tag(name = "凭证管理", description = "凭证创建、修改、验证等操作")
public class CreateCredentialController {

    private final CreateCredentialCmdHandler createCredentialCmdHandler;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "创建凭证", description = "创建一个新的凭证，包括凭证类型、过期时间和使用次数等信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "凭证创建成功", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createCredential(
            @Parameter(description = "创建凭证的请求参数", required = true)
            @RequestBody @Valid CreateCredentialRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        return createCredentialCmdHandler.handle(request.toCmd());
    }
}