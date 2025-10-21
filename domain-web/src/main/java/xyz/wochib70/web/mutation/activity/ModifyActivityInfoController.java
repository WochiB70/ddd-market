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
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.cmd.ModifyActivityInfoCmdHandler;
import xyz.wochib70.web.AuthorizedThreadLocal;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class ModifyActivityInfoController {

    private final ModifyActivityInfoCmdHandler modifyActivityInfoCmdHandler;

    @PostMapping("/modify-info")
    @Transactional
    @Operation(summary = "修改活动基本信息", description = "修改指定活动的名称、描述等基本信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    public void modifyActivityInfo(
            @Parameter(description = "修改活动基本信息请求参数", required = true)
            @RequestBody @Valid ModifyActivityInfoRequest request
    ) {
        UserId adminId = AuthorizedThreadLocal.getAdminId();
        modifyActivityInfoCmdHandler.handle(request.toCmd());
    }
}