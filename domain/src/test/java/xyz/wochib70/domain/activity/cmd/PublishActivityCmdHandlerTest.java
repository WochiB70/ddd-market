package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityPublishedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
class PublishActivityCmdHandlerTest extends ActivityTestBase {


    @Resource
    PublishActivityCmdHandler handler;

    @Test
    void publishActivityTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(0L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.INIT);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new PublishActivityCmd(activity.getActivityId()));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityPublishedEvent, "事件类型应该为ActivityPublishedEvent");
            Assert.isTrue(((ActivityPublishedEvent) event).getActivityId().equals(activity.getActivityId()), "事件中的活动ID应该与发布ID一致");
        }
    }

    @Test
    void publishActivityFailTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(0L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        try {
            handler.handle(new PublishActivityCmd(activity.getActivityId()));
        } catch (Exception e) {
            Assert.isTrue(e instanceof ActivityStatusException, "发布失败");
            Assert.isTrue(e.getMessage().contains("Activity已经开始或者被关闭不能再次发布"), "发布失败");
        }
    }
}