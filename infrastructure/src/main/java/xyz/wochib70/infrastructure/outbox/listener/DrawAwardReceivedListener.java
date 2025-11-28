package xyz.wochib70.infrastructure.outbox.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.wochib70.domain.draw.events.AwardReceivedEvent;
import xyz.wochib70.infrastructure.outbox.OutBoxDao;
import xyz.wochib70.infrastructure.outbox.OutBoxEntity;
import xyz.wochib70.infrastructure.outbox.OutBoxType;

@Slf4j
@RequiredArgsConstructor
@Component
public class DrawAwardReceivedListener {


    private final OutBoxDao outBoxDao;

    private final ObjectMapper objectMapper;

    @Value("${outbox.draw-award-received.topic}")
    private String topic;

    @Value("${outbox.draw-award-received.max-retry-count}")
    private Integer maxRetryCount = 3;

    @EventListener
    @Transactional(propagation = Propagation.MANDATORY)
    public void onEvent(AwardReceivedEvent event) {
        try {
            String content = objectMapper.writeValueAsString(event);
            OutBoxEntity entity = new OutBoxEntity();
            entity.setDestination(topic);
            entity.setContent(content);
            entity.setType(OutBoxType.READY);
            entity.setMaxRetryCount(maxRetryCount);
            outBoxDao.save(entity);
            log.info("保存消息到发件箱成功，{}", content);
        } catch (JsonProcessingException e) {
            log.error("保存消息到发件箱的时候发生异常， {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
