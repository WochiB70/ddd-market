package xyz.wochib70.web.mutation.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.credential.cmd.ModifyCredentialUnusedCountCmd;

@Data
@Schema(description = "修改凭证未使用次数请求")
public class ModifyCredentialUnusedCountRequest {

    @NotNull
    @Schema(description = "凭证ID", example = "789")
    private Long credentialId;
    
    @NotNull
    @Min(0)
    @Schema(description = "未使用次数", example = "15")
    private int unusedCount;

    public ModifyCredentialUnusedCountCmd toCmd() {
        return new ModifyCredentialUnusedCountCmd(new DefaultIdentifierId<>(credentialId), unusedCount);
    }
}