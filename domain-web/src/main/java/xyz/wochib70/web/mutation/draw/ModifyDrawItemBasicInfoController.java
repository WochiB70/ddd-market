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
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemBasicInfoCmdHandler;

@RestController
@RequestMapping("/draw")
@RequiredArgsConstructor
@Tag(name = "抽奖管理", description = "抽奖池创建、物品管理、抽奖操作等")
public class ModifyDrawItemBasicInfoController {

    private final ModifyDrawItemBasicInfoCmdHandler modifyDrawItemBasicInfoCmdHandler;

    @PostMapping("/modify-item-basic-info")
    @Transactional
    @Operation(summary = "修改抽奖物品基本信息", description = "修改抽奖物品的名称、描述等基本信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "抽奖物品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyDrawItemBasicInfo(
            @Parameter(description = "修改抽奖物品基本信息请求参数", required = true)
            @RequestBody @Valid ModifyDrawItemBasicInfoRequest request
    ) {
        modifyDrawItemBasicInfoCmdHandler.handle(request.toCmd());
    }
}