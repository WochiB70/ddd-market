package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemInventoryCmd;
import xyz.wochib70.domain.redeem.RedeemItemInventory;
import xyz.wochib70.domain.redeem.RedeemItemInventoryType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改兑换商品库存请求")
public class ModifyRedeemItemInventoryRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;
    
    @NotNull
    @Schema(description = "库存类型", example = "LIMITED")
    private RedeemItemInventoryType type;
    
    @Schema(description = "有效数量", example = "100")
    private Integer validCount;

    public ModifyRedeemItemInventoryCmd toCmd() {
        RedeemItemInventory inventory = new RedeemItemInventory(type, validCount);
        return new ModifyRedeemItemInventoryCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                inventory
        );
    }
}