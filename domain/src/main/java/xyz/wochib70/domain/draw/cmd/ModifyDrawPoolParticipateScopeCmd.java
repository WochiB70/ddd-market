package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPoolParticipateScope;

public record ModifyDrawPoolParticipateScopeCmd(
        IdentifierId<Long> drawPoolId,
        DrawPoolParticipateScope scope
) {
}
