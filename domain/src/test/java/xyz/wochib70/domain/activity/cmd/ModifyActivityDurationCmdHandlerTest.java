package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityDurationModifiedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModifyActivityDurationCmdHandlerTest extends ActivityTestBase {

    @Resource
    ModifyActivityDurationCmdHandler handler;

    @Test
    void modifyActivityDurationTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);
        handler.handle(new ModifyActivityDurationCmd(
                activity.getActivityId(),
                new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(2))
        ));
        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityDurationModifiedEvent, "事件类型应该为ActivityDurationModifiedEvent");
            Assert.isTrue(((ActivityDurationModifiedEvent) event).getActivityId().equals(activity.getActivityId()), "事件中的活动ID应该与修改的ID一致");
        }
    }

    @Test
    void modifyActivityDurationFailTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);
        try {
            handler.handle(new ModifyActivityDurationCmd(
                    activity.getActivityId(),
                    null
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof NullPointerException, "应该抛出IllegalArgumentException");
            Assert.isTrue(e.getMessage().contains("ActivityDuration不能为null"), "异常信息应该包含'duration不能为null'");
        }
    }

    @Test
    void modifyActivityDurationFailTest2() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        try {
            handler.handle(new ModifyActivityDurationCmd(
                    activity.getActivityId(),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().minusDays(2))
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof IllegalArgumentException, "应该抛出IllegalArgumentException");
            Assert.isTrue(e.getMessage().contains("开始时间不能大于结束时间"), "异常信息应该包含'duration不能为null'");
        }
    }

    @Test
    void modifyActivityDurationFailTest3() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        try {
            handler.handle(new ModifyActivityDurationCmd(
                    activity.getActivityId(),
                    new ActivityDuration(null, LocalDateTime.now().minusDays(2))
            ));
            fail("应该抛出异常");
        } catch (Exception e) {
            // 预期会抛出异常
            Assert.isTrue(e instanceof IllegalArgumentException, "应该抛出IllegalArgumentException");
            Assert.isTrue(e.getMessage().contains("当不存在startTime的时候，endTime的事件不能在当前时间之前"), "异常信息应该包含'duration不能为null'");
        }

    }

}