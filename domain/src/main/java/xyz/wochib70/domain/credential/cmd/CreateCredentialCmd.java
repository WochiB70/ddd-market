package xyz.wochib70.domain.credential.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.CredentialDuration;

public record CreateCredentialCmd(
        IdentifierId<Long> activityId,
        CredentialDuration duration,
        Integer unusedCount,
        UserId userId
) {
}
