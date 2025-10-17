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
import xyz.wochib70.domain.credential.cmd.ModifyCredentialUnusedCountCmdHandler;

@RestController
@RequestMapping("/credential")
@RequiredArgsConstructor
@Tag(name = "凭证管理", description = "凭证创建、修改、验证等操作")
public class ModifyCredentialUnusedCountController {

    private final ModifyCredentialUnusedCountCmdHandler modifyCredentialUnusedCountCmdHandler;

    @PostMapping("/modify-unused-count")
    @Transactional
    @Operation(summary = "修改凭证剩余使用次数", description = "修改指定凭证的剩余使用次数，增加或减少凭证的可使用次数")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "凭证剩余使用次数修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效或新的使用次数不合法"),
        @ApiResponse(responseCode = "404", description = "凭证不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyCredentialUnusedCount(
            @Parameter(description = "修改凭证剩余使用次数的请求参数", required = true)
            @RequestBody @Valid ModifyCredentialUnusedCountRequest request
    ) {
        modifyCredentialUnusedCountCmdHandler.handle(request.toCmd());
    }
}