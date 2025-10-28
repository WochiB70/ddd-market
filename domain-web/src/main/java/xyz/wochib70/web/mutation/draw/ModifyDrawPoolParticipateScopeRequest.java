package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.DrawPoolParticipateScope;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolParticipateScopeCmd;

@Data
@Schema(description = "修改抽奖池参与范围")
public class ModifyDrawPoolParticipateScopeRequest {

    @NotNull
    @Schema(description = "抽奖池id")
    private Long drawPoolId;

    @NotNull
    @Schema(description = "参与范围", example = "GLOBAL")
    private DrawPoolParticipateScope scope;

    public ModifyDrawPoolParticipateScopeCmd toCmd() {
        return new ModifyDrawPoolParticipateScopeCmd(
                new DefaultIdentifierId<>(drawPoolId),
                scope
        );
    }
}
