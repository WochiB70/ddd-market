package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemWeightCmd;

@Data
@Schema(description = "修改抽奖物品权重请求")
public class ModifyDrawItemWeightRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;
    
    @NotNull
    @Min(1)
    @Schema(description = "抽奖物品权重", example = "10")
    private Integer weight;

    public ModifyDrawItemWeightCmd toCmd() {
        return new ModifyDrawItemWeightCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                weight
        );
    }
}