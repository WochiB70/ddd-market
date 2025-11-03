package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.DecreaseDrawItemInventoryCmd;
import xyz.wochib70.domain.draw.cmd.IncreaseDrawItemInventoryCmd;

@Data
@Schema(description = "减少库存")
public class DecreaseDrawItemInventoryRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;

    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;

    @NotNull
    @Schema(description = "减少的库存", example = "1")
    private Integer decrease;


    public DecreaseDrawItemInventoryCmd toCmd() {
        return new DecreaseDrawItemInventoryCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                decrease
        );
    }
}
