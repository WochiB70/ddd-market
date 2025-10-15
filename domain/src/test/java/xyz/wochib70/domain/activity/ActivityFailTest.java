package xyz.wochib70.domain.activity;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;

import java.time.LocalDateTime;
import java.util.Collections;

public class ActivityFailTest extends AggregateTestBase {

    @Test
    void createActivityFail1() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);
        //name 不能为null或是空白
        try {
            factory.createActivity(
                    new ActivityInfo(null, "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "activity name不能为null或是空白");
        }

        try {
            factory.createActivity(
                    new ActivityInfo("", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "activity name不能为null或是空白");
        }

        //name 不能超过50
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity".repeat(10), "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "activity name长度不能超过50");
        }

        //description 不能大于200
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "description".repeat(100), Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "activity description长度不能超过200");
        }

        //images 不能大于5
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.nCopies(6, "image")),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "activity images数量不能超过5");
        }
    }

    @Test
    void createActivityFail2() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);


        //duration 不能为null
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    null,
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是NullPointException");
            Assert.hasText(e.getMessage(), "Activity持续时间不能为null");
        }

        //duration 结束时间不能比现在早
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(null, LocalDateTime.now().minusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "duration的结束时间不能比现在早");
        }

        //duration 开始时间不能早于结束时间
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now().plusDays(1), LocalDateTime.now()),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "duration的开始时间不能早于结束时间");
        }
    }

    @Test
    void createActivityFail3() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);


        //countLimit 不能为null
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    null,
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "Activity人数限制不能为null");
        }

        //countLimit type 不能为null
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(null, 0),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是NullPointException");
            Assert.hasText(e.getMessage(), "限制类型不能为空");
        }

        //countLimit count 不能小于0
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, -1),
                    false,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "限制数量不能小于0");
        }
    }

    @Test
    void createActivityFail4() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);

        //credentialLimit 不能为null
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    null,
                    ActivityAwardType.REDEEM
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是NullPointException");
            Assert.hasText(e.getMessage(), "Activity凭证限制不能为null");
        }
    }

    @Test
    void createActivityFail5() {
        ActivityIdGenerator activityIdGenerator = Mockito.mock(ActivityIdGenerator.class);
        Mockito.when(activityIdGenerator.nextActivityId())
                .thenReturn(new DefaultIdentifierId<>(0L));

        ActivityFactory factory = new ActivityFactory(activityIdGenerator);

        //awardType 不能为null
        try {
            factory.createActivity(
                    new ActivityInfo("Test Activity", "This is a test activity", Collections.emptyList()),
                    new ActivityDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                    new ActivityCountLimit(CountLimitType.INFINITE, 0),
                    false,
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是NullPointException");
            Assert.hasText(e.getMessage(), "Activity奖品类型不能为null");
        }
    }

    @Test
    void modifyActivityInfoFailTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        try {
            activity.modifyActivityInfo(
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "ActivityInfo不能为null");
        }
    }

    @Test
    void modifyActivityDurationFailTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        try {
            activity.modifyDuration(
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "ActivityDuration不能为null");
        }
    }

    @Test
    void modifyActivityCountLimitFailTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        try {
            activity.modifyCountLimit(
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "ActivityCountLimit不能为null");
        }
    }

    @Test
    void modifyActivityCredentialLimitFailTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        try {
            activity.modifyCredentialLimit(
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "ActivityCredentialLimit不能为null");
        }
    }

    @Test
    void modifyActivityAwardTypeFailTest() {
        IdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        ActivityImpl activity = new ActivityImpl(id);
        try {
            activity.modifyAwardType(
                    null
            );
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "应该是IllegalArgumentException");
            Assert.hasText(e.getMessage(), "ActivityAwardType不能为null");
        }
    }

}
