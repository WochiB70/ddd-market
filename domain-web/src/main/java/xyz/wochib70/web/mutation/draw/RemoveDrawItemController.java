package xyz.wochib70.web.mutation.draw;

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
import xyz.wochib70.domain.draw.cmd.RemoveDrawItemCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class RemoveDrawItemController {

    private final RemoveDrawItemCmdHandler removeDrawItemCmdHandler;

    @PostMapping("/remove-item")
    @Transactional
    @Operation(summary = "移除抽奖物品", description = "从抽奖池中移除指定的抽奖物品")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "移除成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "抽奖物品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void removeDrawItem(
            @Parameter(description = "移除抽奖物品请求参数", required = true)
            @RequestBody @Valid RemoveDrawItemRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        removeDrawItemCmdHandler.handle(request.toCmd());
    }
}