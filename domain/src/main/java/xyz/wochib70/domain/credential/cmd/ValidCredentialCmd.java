package xyz.wochib70.domain.credential.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ValidCredentialCmd(
        IdentifierId<Long> credentialId
) {
}
