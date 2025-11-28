package xyz.wochib70.infrastructure.outbox.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import xyz.wochib70.domain.usertask.cmd.ReceiveAndCompleteTaskByEventCmdHandler;

@RocketMQMessageListener(
        topic = "userOperation",
        consumerGroup = "marketGroup"
)
@Slf4j
@RequiredArgsConstructor
@Component
public class UserOperationEventConsumer implements RocketMQListener<UserOperationEvent> {

    private final ReceiveAndCompleteTaskByEventCmdHandler receiveAndCompleteTaskByEventCmdHandler;

    @Transactional
    @Override
    public void onMessage(@Validated UserOperationEvent message) {
        receiveAndCompleteTaskByEventCmdHandler.handle(message.toCmd());
    }
}
