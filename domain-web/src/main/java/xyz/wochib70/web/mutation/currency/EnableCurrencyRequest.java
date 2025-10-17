package xyz.wochib70.web.mutation.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.currency.cmd.EnableCurrencyCmd;

@Data
@Schema(description = "启用货币请求")
public class EnableCurrencyRequest {

    @NotNull
    @Schema(description = "货币ID", example = "1")
    private Long currencyId;

    public EnableCurrencyCmd toCmd() {
        return new EnableCurrencyCmd(new DefaultIdentifierId<>(currencyId));
    }
}