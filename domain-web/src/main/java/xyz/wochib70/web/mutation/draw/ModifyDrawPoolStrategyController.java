package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolStrategyCmdHandler;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class ModifyDrawPoolStrategyController {

    private final ModifyDrawPoolStrategyCmdHandler modifyDrawPoolStrategyCmdHandler;

    @PostMapping("/modify-pool-strategy")
    @Operation(summary = "修改抽奖池策略", description = "修改抽奖池的抽奖策略（如权重、随机等）")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "抽奖池不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyDrawPoolStrategy(
            @Parameter(description = "修改抽奖池策略请求参数", required = true)
            @RequestBody @Valid ModifyDrawPoolStrategyRequest request
    ) {
        modifyDrawPoolStrategyCmdHandler.handle(request.toCmd());
    }
}