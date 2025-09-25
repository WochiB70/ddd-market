package xyz.wochib70.domain.task.cmd;

import xyz.wochib70.domain.IdentifierId;

public record DisableTaskCmd(
        IdentifierId<Long> taskId
) {
}
