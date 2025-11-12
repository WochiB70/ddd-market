package xyz.wochib70.domain.activity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.events.*;

import java.time.LocalDateTime;
import java.util.Collections;

public class ActivityTest extends AggregateTestBase {


    @Test
    void createActivityTest() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);
        Activity activity = factory.createActivity(
                new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new ActivityCountLimit(CountLimitType.INFINITE, 0),
                false,
                ActivityAwardType.REDEEM
        );

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityCreatedEvent, "事件类型应该为ActivityCreatedEvent");
        }
    }



    @Test
    void modifyActivityInfoTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.modifyActivityInfo(new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityInfoModifiedEvent, "事件类型应该为ActivityModifiedEvent");
        }
    }

    @Test
    void modifyActivityDurationTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.modifyDuration(new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityDurationModifiedEvent, "事件类型应该为ActivityDurationModifiedEvent");
        }
    }

    @Test
    void modifyActivityCountLimitTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.modifyCountLimit(new ActivityCountLimit(CountLimitType.INFINITE, 0));

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityCountLimitModifiedEvent, "事件类型应该为ActivityCountLimitModifiedEvent");
        }
    }

    @Test
    void modifyActivityCredentialLimitTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.modifyCredentialLimit(false);

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityCredentialLimitModifiedEvent, "事件类型应该为ActivityCredentialLimitModifiedEvent");
        }
    }

    @Test
    void modifyActivityAwardTypeTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.modifyAwardType(ActivityAwardType.REDEEM);

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityAwardTypeModifiedEvent, "事件类型应该为ActivityAwardTypeModifiedEvent");
        }
    }

    @Test
    void publishActivityTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.setStatus(ActivityStatus.INIT);

        activity.publish();

        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityPublishedEvent, "事件类型应该为ActivityPublishedEvent");
        }
    }

    @Test
    void startActivityTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.setStatus(ActivityStatus.PUBLISHED);
        activity.setDuration(new ActivityDuration(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)));
        activity.start();
        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityStartedEvent, "事件类型应该为ActivityStartedEvent");
        }
    }


    @Test
    void closeActivityTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        activity.setStatus(ActivityStatus.ACTIVE);

        activity.close();
        for (Object event : activity.getEvents()) {
            Assert.isTrue(event instanceof ActivityClosedEvent, "事件类型应该为ActivityClosedEvent");
        }
    }
}
