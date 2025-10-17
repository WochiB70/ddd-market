package xyz.wochib70.web.mutation.currency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.currency.cmd.ModifyCurrencyInfoCmdHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Tag(name = "货币管理", description = "货币创建、启用、停用等操作")
public class ModifyCurrencyInfoController {

    private final ModifyCurrencyInfoCmdHandler modifyCurrencyInfoCmdHandler;

    @PostMapping("/modify-info")
    @Transactional
    @Operation(summary = "修改货币信息", description = "修改指定货币的基本信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "货币不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public void modifyCurrencyInfo(
            @Parameter(description = "修改货币信息请求参数", required = true)
            @RequestBody @Valid ModifyCurrencyInfoRequest request
    ) {
        modifyCurrencyInfoCmdHandler.handle(request.toCmd());
    }
}