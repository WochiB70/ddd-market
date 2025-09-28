package xyz.wochib70.domain.activity.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyActivityCredentialLimitCmd(
        IdentifierId<Long> activityId,
        Boolean credentialLimit
) {
}
