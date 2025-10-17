package xyz.wochib70.web.mutation.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.cmd.ModifyCredentialDurationCmd;
import xyz.wochib70.domain.credential.CredentialDuration;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Schema(description = "修改凭证有效期请求")
public class ModifyCredentialDurationRequest {

    @NotNull
    @Schema(description = "凭证ID", example = "789")
    private Long credentialId;
    
    @NotNull
    @Schema(description = "开始时间", example = "2023-01-01T00:00:00")
    private LocalDateTime startTime;
    
    @NotNull
    @Schema(description = "过期时间", example = "2023-12-31T23:59:59")
    private LocalDateTime expiredTime;

    public ModifyCredentialDurationCmd toCmd() {
        CredentialDuration duration = new CredentialDuration(startTime, expiredTime);
        return new ModifyCredentialDurationCmd(new DefaultIdentifierId<>(credentialId), duration);
    }
}