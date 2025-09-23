package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.draw.DrawPool;
import xyz.wochib70.domain.draw.DrawPoolFactory;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@RequiredArgsConstructor
@Service
public class CreateDrawPoolCmdHandler {

    private final DrawPoolFactory drawPoolFactory;

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(CreateDrawPoolCmd cmd) {
        DrawPool drawPool = drawPoolFactory.create(
                cmd.name(),
                cmd.activityId(),
                cmd.strategyType()
        );
        drawPoolRepository.save(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
    }

}
