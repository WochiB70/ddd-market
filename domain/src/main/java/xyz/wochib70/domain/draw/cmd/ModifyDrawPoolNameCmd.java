package xyz.wochib70.domain.draw.cmd;

import xyz.wochib70.domain.IdentifierId;

public record ModifyDrawPoolNameCmd(
        IdentifierId<Long> drawPoolId,
        String name
) {


}
