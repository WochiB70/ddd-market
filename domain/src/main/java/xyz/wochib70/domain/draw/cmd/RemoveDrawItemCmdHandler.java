package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@RequiredArgsConstructor
@Service
public class RemoveDrawItemCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(RemoveDrawItemCmd cmd) {
        DrawPool drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        drawPool.removeDrawItem(cmd.awardId());
        drawPoolRepository.update(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
    }
}
