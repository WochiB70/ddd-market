package xyz.wochib70.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class OutBoxScheduler {

    public static final int BATCH_COUNT = 100;

    private final OutBoxRepository outBoxRepository;

    private final RocketMQSender sender;

    @Scheduled(cron = "* 0/2  * * * ?")
    public void scanUnprocessedOutBox() {
        List<OutBoxEntity> outBoxEntityList = outBoxRepository.queryBatchAndSetProcessing(BATCH_COUNT);
        if (outBoxEntityList.isEmpty()) {
            return;
        }
        log.info("开始处理未处理的出站消息,共{}条", outBoxEntityList.size());
        for (OutBoxEntity outBox : outBoxEntityList) {
            try {
                sender.send(outBox.getDestination(), outBox.getTag(), outBox.getContent());
                outBox.setType(OutBoxType.SUCCESS);
            } catch (Exception e) {
                log.error("发送消息失败:[{}], 重试次数 +1", e.getMessage());
                outBox.setRetryCount(outBox.getRetryCount() + 1);
                if (outBox.getRetryCount() > outBox.getMaxRetryCount()) {
                    outBox.setType(OutBoxType.FAIL);
                } else {
                    outBox.setType(OutBoxType.READY);
                }
            }
        }
        outBoxRepository.saveBatch(outBoxEntityList);
        log.info("处理完成");
    }
}
