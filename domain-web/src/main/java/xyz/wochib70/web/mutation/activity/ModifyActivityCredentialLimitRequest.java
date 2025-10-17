package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.cmd.ModifyActivityCredentialLimitCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改活动凭证限制请求")
public class ModifyActivityCredentialLimitRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "是否需要凭证限制", example = "true")
    private Boolean credentialLimit;

    public ModifyActivityCredentialLimitCmd toCmd() {
        return new ModifyActivityCredentialLimitCmd(new DefaultIdentifierId<>(activityId), credentialLimit);
    }
}