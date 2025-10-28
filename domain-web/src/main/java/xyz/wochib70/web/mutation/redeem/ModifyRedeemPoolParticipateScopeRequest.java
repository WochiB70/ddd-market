package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.RedeemParticipateScope;
import xyz.wochib70.domain.redeem.cmd.ModifyRedeemPoolParticipateCmd;

@Data
@Schema(description = "修改抽奖池参与范围")
public class ModifyRedeemPoolParticipateScopeRequest {

    @NotNull
    @Schema(description = "抽奖池id")
    private Long drawPoolId;

    @NotNull
    @Schema(description = "参与范围", example = "GLOBAL")
    private RedeemParticipateScope scope;

    public ModifyRedeemPoolParticipateCmd toCmd() {
        return new ModifyRedeemPoolParticipateCmd(
                new DefaultIdentifierId<>(drawPoolId),
                scope
        );
    }
}
