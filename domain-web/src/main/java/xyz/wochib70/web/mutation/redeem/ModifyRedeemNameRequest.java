package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemNameCmd;

@Data
@Schema(description = "修改兑换名称请求")
public class ModifyRedeemNameRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;
    
    @NotNull
    @Schema(description = "兑换名称", example = "新年兑换活动")
    private String name;

    public ModifyRedeemNameCmd toCmd() {
        return new ModifyRedeemNameCmd(new DefaultIdentifierId<>(redeemId), name);
    }
}