package xyz.wochib70.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RocketMQSender {

    private final RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String tag, String content) {
        Message<String> message = MessageBuilder.withPayload(content)
                .setHeader("tag", tag)
                .build();
        rocketMQTemplate.send(topic, message);
    }
}
