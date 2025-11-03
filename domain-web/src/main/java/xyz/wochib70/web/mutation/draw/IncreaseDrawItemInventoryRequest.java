package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.IncreaseDrawItemInventoryCmd;

@Data
@Schema(description = "增加库存")
public class IncreaseDrawItemInventoryRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;

    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;

    @NotNull
    @Schema(description = "增加的库存", example = "1")
    private Integer increase;


    public IncreaseDrawItemInventoryCmd toCmd() {
        return new IncreaseDrawItemInventoryCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                increase
        );
    }
}
