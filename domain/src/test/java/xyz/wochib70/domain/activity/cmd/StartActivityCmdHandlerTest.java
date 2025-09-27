package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityStartedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
class StartActivityCmdHandlerTest extends ActivityTestBase {

    @Resource
    StartActivityCmdHandler handler;


    @Test
    void startActivityTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(0L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.PUBLISHED);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new StartActivityCmd(activity.getActivityId()));
        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityStartedEvent, "事件类型应该为ActivityStartedEvent");
            Assert.isTrue(((ActivityStartedEvent) event).getActivityId().equals(activity.getActivityId()), "事件中的活动ID应该与发布ID一致");
        }
    }

    @Test
    void startActivityFailTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(0L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.CLOSE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        try {
            handler.handle(new StartActivityCmd(activity.getActivityId()));
        } catch (Exception e) {
            Assert.isTrue(e instanceof ActivityStatusException, "异常类型应该为ActivityStatusException");
        }
    }

    @Test
    void startActivityFailTest2() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(0L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.INIT);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        try {
            handler.handle(new StartActivityCmd(activity.getActivityId()));
        } catch (Exception e) {
            Assert.isTrue(e instanceof ActivityStatusException, "异常类型应该为ActivityStatusException");
        }
    }
}