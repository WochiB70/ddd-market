package xyz.wochib70.web.mutation.draw;

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
import xyz.wochib70.domain.draw.Reward;
import xyz.wochib70.domain.draw.cmd.ParticipateActivityWithDrawPoolCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class ParticipateActivityWithDrawPoolController {

    private final ParticipateActivityWithDrawPoolCmdHandler participateActivityWithDrawPoolCmdHandler;

    @PostMapping("/participate-activity")
    @Transactional
    @Operation(summary = "参与抽奖活动", description = "用户参与抽奖活动进行抽奖")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "抽奖成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "抽奖活动或抽奖池不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public Reward participateActivityWithDrawPool(
            @Parameter(description = "参与抽奖活动请求参数", required = true)
            @RequestBody @Valid ParticipateActivityWithDrawPoolRequest request
    ) {
        UserId userId = AuthorizedThreadLocal.getUserId();
        //TODO QUERY模块完成之后再改
        return participateActivityWithDrawPoolCmdHandler.handle(request.toCmd());
    }
}