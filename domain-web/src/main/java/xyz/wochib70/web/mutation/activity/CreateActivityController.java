package xyz.wochib70.web.mutation.activity;

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
import xyz.wochib70.domain.activity.cmd.CreateActivityCmdHandler;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "活动管理", description = "活动创建、修改、发布等操作")
public class CreateActivityController {

    private final CreateActivityCmdHandler createActivityCmdHandler;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "创建活动", description = "创建一个新的营销活动")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "活动创建成功", 
                    content = @Content(schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createActivity(
            @Parameter(description = "创建活动请求参数", required = true)
            @RequestBody @Valid CreateActivityRequest request
    ) {
        return createActivityCmdHandler.handle(request.toCmd());
    }
}