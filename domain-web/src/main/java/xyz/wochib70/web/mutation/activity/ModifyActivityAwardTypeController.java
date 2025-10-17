package xyz.wochib70.web.mutation.activity;

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
import xyz.wochib70.domain.activity.cmd.ModifyActivityAwardTypeCmdHandler;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class ModifyActivityAwardTypeController {

    private final ModifyActivityAwardTypeCmdHandler modifyActivityAwardTypeCmdHandler;

    @PostMapping("/modify-award-type")
    @Transactional
    @Operation(summary = "修改活动奖励类型", description = "修改指定活动的奖励类型配置")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "修改成功"),
            @ApiResponse(responseCode = "400", description = "请求参数无效"),
            @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    public void modifyActivityAwardType(
            @Parameter(description = "修改活动奖励类型请求参数", required = true)
            @RequestBody @Valid ModifyActivityAwardTypeRequest request
    ) {
        modifyActivityAwardTypeCmdHandler.handle(request.toCmd());
    }
}