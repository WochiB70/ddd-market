package xyz.wochib70.web.mutation.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.cmd.InvalidCredentialCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "使凭证失效请求")
public class InvalidCredentialRequest {

    @NotNull
    @Schema(description = "凭证ID", example = "789")
    private Long credentialId;

    public InvalidCredentialCmd toCmd() {
        return new InvalidCredentialCmd(new DefaultIdentifierId<>(credentialId));
    }
}