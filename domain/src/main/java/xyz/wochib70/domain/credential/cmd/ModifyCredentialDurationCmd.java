package xyz.wochib70.domain.credential.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.credential.CredentialDuration;

public record ModifyCredentialDurationCmd(
        IdentifierId<Long> credentialId,
        CredentialDuration duration
) {
}
