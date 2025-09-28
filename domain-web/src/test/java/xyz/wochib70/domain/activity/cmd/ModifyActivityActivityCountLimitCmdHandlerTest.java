package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityCountLimitModifiedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModifyActivityActivityCountLimitCmdHandlerTest extends ActivityTestBase {

    @Resource
    private ModifyActivityCountLimitCmdHandler handler;

    @Test
    void modifyCountLimitTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new ModifyActivityCountLimitCmd(
                activity.getActivityId(),
                new ActivityCountLimit(CountLimitType.ACTIVITY_COUNT, 10)
        ));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityCountLimitModifiedEvent, "事件类型应该为ActivityCountLimitModifiedEvent");
        }
    }

    @Test
    void modifyCountLimitFailTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        try {
            handler.handle(new ModifyActivityCountLimitCmd(
                    activity.getActivityId(),
                    new ActivityCountLimit(CountLimitType.ACTIVITY_COUNT, -10)
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof IllegalArgumentException, "应该抛出IllegalArgumentException");
        }
    }

    @Test
    void modifyCountLimitFailTest2() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        try {
            handler.handle(new ModifyActivityCountLimitCmd(
                    activity.getActivityId(),
                    new ActivityCountLimit(null, 10)
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof NullPointerException, "应该抛出IllegalArgumentException");
        }
    }

    @Test
    void modifyCountLimitFailTest3() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        try {
            handler.handle(new ModifyActivityCountLimitCmd(
                    activity.getActivityId(),
                    null
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof NullPointerException, "应该抛出IllegalArgumentException");
        }
    }
}