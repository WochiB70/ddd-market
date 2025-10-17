package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.RedeemItemPrice;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemPriceCmd;

@Data
@Schema(description = "修改兑换商品价格请求")
public class ModifyRedeemItemPriceRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;

    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;

    @NotNull
    @Schema(description = "货币ID", example = "1")
    private Long currencyId;

    @NotNull
    @Schema(description = "商品价格", example = "1500")
    private Integer price;

    public ModifyRedeemItemPriceCmd toCmd() {
        RedeemItemPrice redeemItemPrice = new RedeemItemPrice(new DefaultIdentifierId<>(currencyId), price);
        return new ModifyRedeemItemPriceCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                redeemItemPrice
        );
    }
}