package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.cmd.ParticipateActivityWithRedeemCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/redeem")
@RequiredArgsConstructor
@Tag(name = "兑换管理", description = "兑换创建、修改、参与等操作")
public class ParticipateActivityWithRedeemController {

    private final ParticipateActivityWithRedeemCmdHandler participateActivityWithRedeemCmdHandler;

    @PostMapping("/participate-activity")
    @Operation(summary = "参与兑换活动", description = "用户参与兑换活动进行兑换")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "兑换成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "404", description = "兑换活动不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void participateActivityWithRedeem(
            @Parameter(description = "参与兑换活动请求参数", required = true)
            @RequestBody @Valid ParticipateActivityWithRedeemRequest request
    ) {
        participateActivityWithRedeemCmdHandler.handle(request.toCmd());
    }
}