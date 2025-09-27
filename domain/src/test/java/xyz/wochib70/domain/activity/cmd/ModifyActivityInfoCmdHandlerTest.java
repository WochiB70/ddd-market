package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityInfoModifiedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModifyActivityInfoCmdHandlerTest extends ActivityTestBase {

    @Resource
    ModifyActivityInfoCmdHandler handler;

    @Test
    void modifyActivityInfoTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new ModifyActivityInfoCmd(
                activity.getActivityId(),
                new ActivityInfo("New Activity", "This is a new activity", Collections.emptyList())
        ));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityInfoModifiedEvent, "事件类型应该为ActivityInfoModifiedEvent");
            Assert.isTrue(((ActivityInfoModifiedEvent) event).getActivityId().equals(activity.getActivityId()), "事件中的活动ID应该与修改的ID一致");
        }
    }

    @Test
    void modifyActivityInfoFailTest() {
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
            handler.handle(new ModifyActivityInfoCmd(
                    activity.getActivityId(),
                    null
            ));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "修改失败");
            Assert.isTrue(e.getMessage().contains("ActivityInfo不能为null"), "修改失败");
        }
    }

    @Test
    void modifyActivityInfoTest2() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new ModifyActivityInfoCmd(
                activity.getActivityId(),
                new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList())
        ));

        assertTrue(activity.getEvents().isEmpty(), "信息未修改，不应产生事件");
    }

}