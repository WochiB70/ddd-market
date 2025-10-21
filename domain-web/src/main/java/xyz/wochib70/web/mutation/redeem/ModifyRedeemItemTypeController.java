package xyz.wochib70.web.mutation.redeem;

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
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemTypeCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/redeem")
@RequiredArgsConstructor
@Tag(name = "兑换管理", description = "兑换创建、修改、参与等操作")
public class ModifyRedeemItemTypeController {

    private final ModifyRedeemItemTypeCmdHandler modifyRedeemItemTypeCmdHandler;

    @PostMapping("/modify-item-type")
    @Operation(summary = "修改兑换物品类型", description = "修改兑换物品的类型")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "兑换物品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyRedeemItemType(
            @Parameter(description = "修改兑换物品类型请求参数", required = true)
            @RequestBody @Valid ModifyRedeemItemTypeRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyRedeemItemTypeCmdHandler.handle(request.toCmd());
    }
}