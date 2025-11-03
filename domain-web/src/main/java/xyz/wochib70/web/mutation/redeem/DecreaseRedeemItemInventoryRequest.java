package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.cmd.DecreaseRedeemItemInventoryCmd;

@Data
@Schema(description = "减少库存")
public class DecreaseRedeemItemInventoryRequest {

    @NotNull
    @Schema(description = "兑换池ID", example = "1")
    private Long redeemId;

    @NotNull
    @Schema(description = "兑换项ID", example = "1")
    private Long redeemItemId;

    @NotNull
    @Schema(description = "减少的库存", example = "1")
    private Integer decrease;


    public DecreaseRedeemItemInventoryCmd toCmd() {
        return new DecreaseRedeemItemInventoryCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                decrease
        );
    }
}
