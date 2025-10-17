package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemTypeCmd;
import xyz.wochib70.domain.redeem.RedeemItemType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改兑换商品类型请求")
public class ModifyRedeemItemTypeRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;
    
    @NotNull
    @Schema(description = "兑换商品类型", example = "VIRTUAL")
    private RedeemItemType redeemItemType;

    public ModifyRedeemItemTypeCmd toCmd() {
        return new ModifyRedeemItemTypeCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                redeemItemType
        );
    }
}