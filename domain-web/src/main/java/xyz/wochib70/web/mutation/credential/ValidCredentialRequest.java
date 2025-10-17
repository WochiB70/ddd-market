package xyz.wochib70.web.mutation.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.credential.cmd.ValidCredentialCmd;

@Data
@Schema(description = "验证凭证请求")
public class ValidCredentialRequest {

    @NotNull
    @Schema(description = "凭证ID", example = "789")
    private Long credentialId;

    public ValidCredentialCmd toCmd() {
        return new ValidCredentialCmd(new DefaultIdentifierId<>(credentialId));
    }
}