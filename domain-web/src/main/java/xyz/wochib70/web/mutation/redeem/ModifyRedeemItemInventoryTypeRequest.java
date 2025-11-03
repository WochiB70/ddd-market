package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.inventory.InventoryType;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemInventoryTypeCmd;

@Data
@Schema(description = "修改兑换商品库存请求")
public class ModifyRedeemItemInventoryTypeRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;
    
    @NotNull
    @Schema(description = "库存类型", example = "LIMITED")
    private InventoryType type;
    

    public ModifyRedeemItemInventoryTypeCmd toCmd() {
        return new ModifyRedeemItemInventoryTypeCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                type
        );
    }
}