package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.IncreaseDrawItemInventoryCmd;
import xyz.wochib70.domain.redeem.cmd.IncreaseRedeemItemInventoryCmd;

@Data
@Schema(description = "增加库存")
public class IncreaseRedeemItemInventoryRequest {

    @NotNull
    @Schema(description = "兑换池ID", example = "1")
    private Long redeemPoolId;

    @NotNull
    @Schema(description = "兑换项ID", example = "1")
    private Long redeemItemId;

    @NotNull
    @Schema(description = "增加的库存", example = "1")
    private Integer increase;


    public IncreaseRedeemItemInventoryCmd toCmd() {
        return new IncreaseRedeemItemInventoryCmd(
                new DefaultIdentifierId<>(redeemPoolId),
                new DefaultIdentifierId<>(redeemItemId),
                increase
        );
    }
}
