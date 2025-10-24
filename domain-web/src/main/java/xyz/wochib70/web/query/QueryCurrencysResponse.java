package xyz.wochib70.web.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.currency.CurrencyStatus;

@Data
@Schema(description = "货币信息响应")
public class QueryCurrencysResponse {

    @Schema(description = "货币ID")
    private Long id;

    @Schema(description = "货币名称")
    private String name;

    @Schema(description = "货币状态")
    private CurrencyStatus status;
}