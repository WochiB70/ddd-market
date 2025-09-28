package xyz.wochib70.domain.credential.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyCredentialUnusedCountCmd(
        IdentifierId<Long> credentialId,
        int unusedCount
) {
}
