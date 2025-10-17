package xyz.wochib70.web.mutation.currency;

import lombok.Data;
import xyz.wochib70.domain.currency.cmd.CreateCurrencyCmd;
import xyz.wochib70.domain.currency.CurrencyInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "创建货币请求")
public class CreateCurrencyRequest {

    @NotBlank
    @Size(max = 20, message = "Currency name长度不能超过20个字符")
    @Schema(description = "货币名称", example = "金币")
    private String name;
    
    @Size(max = 50, message = "Currency description长度不能超过50个字符")
    @Schema(description = "货币描述", example = "游戏内通用货币")
    private String description;

    public CreateCurrencyCmd toCmd() {
        CurrencyInfo info = new CurrencyInfo(name, description);
        return new CreateCurrencyCmd(info);
    }
}