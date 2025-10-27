package xyz.wochib70.web.mutation.redeem;

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
import xyz.wochib70.domain.redeem.cmd.CreateRedeemCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/redeem")
@RequiredArgsConstructor
@Tag(name = "兑换管理", description = "兑换创建、修改、参与等操作")
public class CreateRedeemController {

    private final CreateRedeemCmdHandler createRedeemCmdHandler;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "创建兑换", description = "创建一个新的兑换活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "兑换创建成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createRedeem(
            @Parameter(description = "创建兑换请求参数", required = true)
            @RequestBody @Valid CreateRedeemRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        return createRedeemCmdHandler.handle(request.toCmd());
    }
}