package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolParticipateScopeCmdHandler;

@AllArgsConstructor
@RestController
@RequestMapping("/draw")
@Tag(name = "抽奖管理", description = "修改奖池的作用范围")
public class ModifyDrawPoolParticipateScopeController {

    private final ModifyDrawPoolParticipateScopeCmdHandler modifyDrawPoolParticipateScopeCmdHandler;


    @PostMapping("/modify-draw-pool-participate-scope")
    @Transactional
    @Operation(summary = "修改抽奖池作用范围", description = "修改抽奖池作用范围")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "修改成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "404", description = "抽奖池不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyDrawPoolParticipateScope(
            @Parameter(description = "修改抽奖池作用范围请求", required = true)
            @RequestBody
            @Validated
            ModifyDrawPoolParticipateScopeRequest request
    ) {
        modifyDrawPoolParticipateScopeCmdHandler.handle(request.toCmd());
    }
}
