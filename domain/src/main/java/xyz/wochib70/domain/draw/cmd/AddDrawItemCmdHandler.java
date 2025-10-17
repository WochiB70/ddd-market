package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@RequiredArgsConstructor
@Service
public class AddDrawItemCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public IdentifierId<Long> handle(AddDrawItemCmd cmd) {
        DrawPool drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        IdentifierId<Long> drawItemId = drawPool.addDrawItem(cmd.drawItemInfo());
        drawPoolRepository.update(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
        return drawItemId;
    }
}
