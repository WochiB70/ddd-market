package xyz.wochib70.web.mutation.currency;

import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.cmd.DeactivateCurrencyCmd;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "停用货币请求")
public class DeactivateCurrencyRequest {

    @NotNull
    @Schema(description = "货币ID", example = "1")
    private Long currencyId;

    public DeactivateCurrencyCmd toCmd() {
        return new DeactivateCurrencyCmd(new DefaultIdentifierId<>(currencyId));
    }
}