package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.DrawPrice;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolPriceCmd;

@Data
@Schema(description = "修改抽奖池价格请求")
public class ModifyDrawPoolPriceRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "货币ID", example = "1")
    private Long currencyId;
    
    @NotNull
    @Min(0)
    @Schema(description = "抽奖价格", example = "100")
    private Integer price;

    public ModifyDrawPoolPriceCmd toCmd() {
        DrawPrice drawPrice = new DrawPrice(new DefaultIdentifierId<>(currencyId), price);
        return new ModifyDrawPoolPriceCmd(new DefaultIdentifierId<>(drawPoolId), drawPrice);
    }
}