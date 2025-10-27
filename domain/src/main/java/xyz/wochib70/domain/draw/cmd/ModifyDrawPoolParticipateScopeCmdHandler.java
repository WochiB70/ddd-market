package xyz.wochib70.domain.draw.cmd;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@AllArgsConstructor
@Component
public class ModifyDrawPoolParticipateScopeCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyDrawPoolParticipateScopeCmd cmd) {
        DrawPool drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        drawPool.modifyParticipateScope(cmd.scope());
        drawPoolRepository.update(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
    }

}
