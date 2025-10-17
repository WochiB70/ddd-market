package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolStrategyCmd;
import xyz.wochib70.domain.draw.DrawStrategyType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改抽奖池策略请求")
public class ModifyDrawPoolStrategyRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "抽奖策略类型", example = "WEIGHTED_RANDOM")
    private DrawStrategyType strategyType;

    public ModifyDrawPoolStrategyCmd toCmd() {
        return new ModifyDrawPoolStrategyCmd(new DefaultIdentifierId<>(drawPoolId), strategyType);
    }
}