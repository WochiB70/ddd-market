package xyz.wochib70.web.mutation.redeem;


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
import xyz.wochib70.domain.redeem.cmd.DecreaseRedeemItemInventoryCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/redeem")
@RequiredArgsConstructor
@Tag(name = "兑换管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class DecreaseRedeemItemInventoryController {


    private final DecreaseRedeemItemInventoryCmdHandler decreaseRedeemItemInventoryCmdHandler;

    @PostMapping("/decrease-item-inventory")
    @Transactional
    @Operation(summary = "减少兑换物品库存", description = "减少兑换物品的库存数量")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "增加成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "404", description = "抽奖物品不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyDrawItemInventory(
            @Parameter(description = "减少兑换物品库存请求参数", required = true)
            @RequestBody @Valid DecreaseRedeemItemInventoryRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        decreaseRedeemItemInventoryCmdHandler.handle(request.toCmd());
    }
}
