package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.cmd.RemoveRedeemItemCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "移除兑换商品请求")
public class RemoveRedeemItemRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;

    public RemoveRedeemItemCmd toCmd() {
        return new RemoveRedeemItemCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId)
        );
    }
}