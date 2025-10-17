package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.*;
import xyz.wochib70.domain.redeem.cmd.AddRedeemItemCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "添加兑换商品请求")
public class AddRedeemItemRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "商品名称", example = "iPhone 15")
    private String name;
    
    @Schema(description = "商品描述", example = "最新款iPhone手机")
    private String description;
    
    @NotNull
    @Schema(description = "商品类型", example = "PHYSICAL")
    private RedeemItemType type;
    
    @NotNull
    @Schema(description = "货币ID", example = "1")
    private Long currencyId;
    
    @NotNull
    @Schema(description = "商品价格", example = "1000")
    private Integer price;
    
    @Schema(description = "有效数量", example = "100")
    private Integer validCount;

    public AddRedeemItemCmd toCmd() {
        RedeemItemPrice redeemItemPrice = new RedeemItemPrice(new DefaultIdentifierId<>(currencyId), price);
        RedeemItemInventory redeemItemInventory = new RedeemItemInventory(
            validCount != null ? RedeemItemInventoryType.LIMITED : RedeemItemInventoryType.INFINITE,
            validCount
        );
        RedeemItemInfo info = new RedeemItemInfo(name, description, type, redeemItemPrice, redeemItemInventory);
        return new AddRedeemItemCmd(new DefaultIdentifierId<>(redeemId), info);
    }
}