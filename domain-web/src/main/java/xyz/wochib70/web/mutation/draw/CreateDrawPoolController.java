package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.CreateDrawPoolCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class CreateDrawPoolController {

    private final CreateDrawPoolCmdHandler createDrawPoolCmdHandler;

    @PostMapping("/create-pool")
    @Transactional
    @Operation(summary = "创建抽奖池", description = "创建一个新的抽奖池")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createDrawPool(
            @Parameter(description = "创建抽奖池请求参数", required = true)
            @RequestBody @Valid CreateDrawPoolRequest request
    ) {
        return createDrawPoolCmdHandler.handle(request.toCmd());
    }
}