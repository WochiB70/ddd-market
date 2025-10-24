package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolFactory;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@RequiredArgsConstructor
@Service
public class CreateDrawPoolCmdHandler {

    private final DrawPoolFactory drawPoolFactory;

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public IdentifierId<Long> handle(CreateDrawPoolCmd cmd) {
        DrawPool drawPool = drawPoolFactory.create(
                cmd.name(),
                cmd.activityId(),
                cmd.strategyType(),
                cmd.drawPrice()
        );
        drawPoolRepository.save(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
        return drawPool.getDrawPoolId();
    }

}
