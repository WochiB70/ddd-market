package xyz.wochib70.domain.draw.cmd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import xyz.wochib70.domain.draw.DrawPoolRepository;

@RequiredArgsConstructor
@Service
public class ModifyDrawItemBasicInfoCmdHandler {

    private final DrawPoolRepository drawPoolRepository;

    private final ApplicationEventPublisher eventPublisher;

    public void handle(ModifyDrawItemBasicInfoCmd cmd) {
        var drawPool = drawPoolRepository.findByIdOrThrow(cmd.drawPoolId());
        drawPool.modifyDrawItemBasicInfo(cmd.awardId(), cmd.name(), cmd.description());
        drawPoolRepository.update(drawPool);
        drawPool.getEvents().forEach(eventPublisher::publishEvent);
    }
}
