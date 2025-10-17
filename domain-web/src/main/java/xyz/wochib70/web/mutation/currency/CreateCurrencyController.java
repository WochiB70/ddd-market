package xyz.wochib70.web.mutation.currency;

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
import xyz.wochib70.domain.currency.cmd.CreateCurrencyCmdHandler;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Tag(name = "货币管理", description = "货币创建、启用、停用等操作")
public class CreateCurrencyController {

    private final CreateCurrencyCmdHandler createCurrencyCmdHandler;

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "创建货币", description = "创建新的货币类型")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功", 
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = IdentifierId.class))),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    public IdentifierId<Long> createCurrency(
            @Parameter(description = "创建货币请求参数", required = true)
            @RequestBody @Valid CreateCurrencyRequest request
    ) {
        return createCurrencyCmdHandler.handle(request.toCmd());
    }
}