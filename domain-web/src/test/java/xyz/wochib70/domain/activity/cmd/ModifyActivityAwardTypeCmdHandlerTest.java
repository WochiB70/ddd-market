package xyz.wochib70.domain.activity.cmd;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.events.ActivityAwardTypeModifiedEvent;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.util.Assert.isTrue;

@SpringBootTest
class ModifyActivityAwardTypeCmdHandlerTest extends ActivityTestBase {

    @Resource
    private ModifyActivityAwardTypeCmdHandler handler;

    @Test
    void modifyAwardTypeTest() {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);

        Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                .thenReturn(activity);

        handler.handle(new ModifyActivityAwardTypeCmd(
                activity.getActivityId(),
                ActivityAwardType.DRAW
        ));

        for (Object event : activity.getEvents()) {
            isTrue(event instanceof ActivityAwardTypeModifiedEvent, "事件类型应该为ActivityAwardTypeModifiedEvent");
        }
    }

    @Test
    void handleFailTest() {
        // Given
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(1L));
        activity.setInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));
        activity.setDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setAwardType(ActivityAwardType.REDEEM);
        activity.setCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));
        activity.setCredentialLimit(false);
        // When & Then
        try {
            Mockito.when(repository.queryActivityByIdOrThrow(activity.getActivityId()))
                    .thenReturn(activity);

            handler.handle(new ModifyActivityAwardTypeCmd(
                    activity.getActivityId(),
                    null
            ));
        } catch (Exception e) {
            isTrue(e instanceof NullPointerException, "应该抛出IllegalArgumentException");
            isTrue(e.getMessage().contains("AwardType不能为null"), "错误消息应该包含'AwardType不能为null'");
        }
    }
}