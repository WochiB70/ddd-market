package xyz.wochib70.infrastructure.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.AbstractAggregateEvent;

@AllArgsConstructor
@Component
public class DomainEventListener {

    private final ObjectMapper objectMapper;

    private final EventDao eventDao;

    @EventListener
    public void handle(AbstractAggregateEvent<Long> event) throws JsonProcessingException {
        EventEntity entity = new EventEntity();
        entity.setId(event.eventId().getId());
        entity.setAggregateClass(event.aggregateClass().getName());
        entity.setEventClass(event.eventClass().getName());
        entity.setCreatedTime(event.createTime());
        entity.setContent(objectMapper.writeValueAsString(event));
        eventDao.save(entity);
    }
}
