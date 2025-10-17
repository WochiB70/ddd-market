package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemItemBasicInfoCmd;

@Data
@Schema(description = "修改兑换商品基本信息请求")
public class ModifyRedeemItemBasicInfoRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;
    
    @NotNull
    @Schema(description = "商品名称", example = "iPhone 15 Pro")
    private String name;
    
    @NotNull
    @Schema(description = "商品描述", example = "最新款iPhone Pro手机")
    private String description;

    public ModifyRedeemItemBasicInfoCmd toCmd() {
        return new ModifyRedeemItemBasicInfoCmd(
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                name,
                description
        );
    }
}