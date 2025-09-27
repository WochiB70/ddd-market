package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityClosedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
class CloseActivityCmdHandlerTest extends ActivityTestBase {

    @Resource
    private CloseActivityCmdHandler handler;


    @Test
    public void closeActivityTest1() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(new DefaultIdentifierId<>(1L)))
                .thenReturn(activity);

        handler.handle(new CloseActivityCmd(new DefaultIdentifierId<>(1L)));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityClosedEvent, "事件类型应该为ActivityClosedEvent");
        }
    }

    @Test
    public void closeActivityTest2() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.CLOSE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(new DefaultIdentifierId<>(1L)))
                .thenReturn(activity);

        handler.handle(new CloseActivityCmd(new DefaultIdentifierId<>(1L)));

        Assert.isTrue(activity.getEvents().isEmpty(), "Close状态的Activity关闭不应该发出事件");

    }

    @Test
    public void closeActivityFailTest() {
        try {
            ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
            activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
            activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            activity.setStatus(ActivityStatus.INIT);
            activity.setAwardType(ActivityAwardType.REDEEM);
            activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
            activity.setCredentialLimit(false);

            Mockito.when(repository.queryActivityByIdOrThrow(new DefaultIdentifierId<>(1L)))
                    .thenReturn(activity);

            handler.handle(new CloseActivityCmd(new DefaultIdentifierId<>(1L)));
        } catch (ActivityStatusException e) {
            Assert.isTrue(e.getMessage().contains("Activity未开始不能关闭"), "活动不存在");
        }
    }
}