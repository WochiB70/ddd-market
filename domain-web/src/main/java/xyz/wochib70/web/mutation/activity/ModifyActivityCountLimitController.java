package xyz.wochib70.web.mutation.activity;

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
import xyz.wochib70.domain.activity.cmd.ModifyActivityCountLimitCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class ModifyActivityCountLimitController {

    private final ModifyActivityCountLimitCmdHandler modifyActivityCountLimitCmdHandler;

    @PostMapping("/modify-count-limit")
    @Transactional
    @Operation(summary = "修改活动次数限制", description = "修改指定活动的参与次数限制配置")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "修改成功"),
            @ApiResponse(responseCode = "400", description = "请求参数无效"),
            @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    public void modifyActivityCountLimit(
            @Parameter(description = "修改活动次数限制请求参数", required = true)
            @RequestBody @Valid ModifyActivityCountLimitRequest request
    ) {
        modifyActivityCountLimitCmdHandler.handle(request.toCmd());
    }
}