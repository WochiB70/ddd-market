package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.DrawPrice;
import xyz.wochib70.domain.draw.DrawStrategyType;
import xyz.wochib70.domain.draw.cmd.CreateDrawPoolCmd;

@Data
@Schema(description = "创建抽奖池请求")
public class CreateDrawPoolRequest {

    @NotBlank
    @Schema(description = "抽奖池名称", example = "新年抽奖池")
    private String name;

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;

    @NotNull
    @Schema(description = "抽奖策略类型", example = "WEIGHTED_RANDOM")
    private DrawStrategyType strategyType;

    @NotNull
    @Schema(description = "抽奖价格")
    private DrawPriceRequest drawPrice;


    public record DrawPriceRequest(
            @NotNull
            @Schema(description = "币种ID", example = "1")
            Long currencyId,
            @NotNull
            @Schema(description = "价格", example = "100")
            Integer price
    ) {
    }

    public CreateDrawPoolCmd toCmd() {
        return new CreateDrawPoolCmd(
                name,
                new DefaultIdentifierId<>(activityId),
                strategyType,
                new DrawPrice(new DefaultIdentifierId<>(drawPrice.currencyId),
                        drawPrice.price
                ));
    }
}