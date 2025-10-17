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
import xyz.wochib70.domain.activity.cmd.StartActivityCmdHandler;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class StartActivityController {

    private final StartActivityCmdHandler startActivityCmdHandler;

    @PostMapping("/start")
    @Transactional
    @Operation(summary = "启动活动", description = "启动指定的活动，使其开始接受用户参与")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "启动成功"),
            @ApiResponse(responseCode = "400", description = "请求参数无效"),
            @ApiResponse(responseCode = "404", description = "活动不存在")
    })
    public void startActivity(
            @Parameter(description = "启动活动请求参数", required = true)
            @RequestBody @Valid StartActivityRequest request
    ) {
        startActivityCmdHandler.handle(request.toCmd());
    }
}