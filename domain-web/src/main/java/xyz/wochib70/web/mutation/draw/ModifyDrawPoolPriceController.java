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
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolPriceCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class ModifyDrawPoolPriceController {

    private final ModifyDrawPoolPriceCmdHandler modifyDrawPoolPriceCmdHandler;

    @PostMapping("/modify-pool-price")
    @Operation(summary = "修改抽奖池价格", description = "修改抽奖池的抽奖价格")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "抽奖池不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyDrawPoolPrice(
            @Parameter(description = "修改抽奖池价格请求参数", required = true)
            @RequestBody @Valid ModifyDrawPoolPriceRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyDrawPoolPriceCmdHandler.handle(request.toCmd());
    }
}