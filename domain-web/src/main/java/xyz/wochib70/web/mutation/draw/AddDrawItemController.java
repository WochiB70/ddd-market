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
import xyz.wochib70.domain.draw.cmd.AddDrawItemCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class AddDrawItemController {

    private final AddDrawItemCmdHandler addDrawItemCmdHandler;

    @PostMapping("/add-item")
    @Transactional
    @Operation(summary = "添加抽奖物品", description = "向指定抽奖池添加新的抽奖物品")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "添加成功", content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> addDrawItem(
            @Parameter(description = "添加抽奖物品请求参数", required = true)
            @RequestBody @Valid AddDrawItemRequest request
    ) {
        return addDrawItemCmdHandler.handle(request.toCmd());
    }
}