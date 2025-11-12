package xyz.wochib70.domain.task;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import xyz.wochib70.domain.activity.ActivityDuration;

import java.time.LocalDateTime;

public class TaskDurationTest {

    @Test
    void testValid() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDuration activityDuration = new ActivityDuration(
                now.minusDays(1),
                now.plusDays(1)
        );
        TaskDuration duration = new TaskDuration.Builder(activityDuration)
                .startTime(now.minusDays(1))
                .expiredTime(now.plusDays(1))
                .build();
        Assert.isTrue(duration.valid(), "任务时间有效");
    }

    @Test
    void testInvalid() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDuration activityDuration = new ActivityDuration(
                now.minusDays(1),
                now
        );
        TaskDuration duration = new TaskDuration.Builder(activityDuration)
                .startTime(now.minusDays(1))
                .expiredTime(now)
                .build();
        Assert.isTrue(!duration.valid(), "任务时间无效");
    }

    @Test
    void testNullActivityDuration() {
        LocalDateTime now = LocalDateTime.now();
        new TaskDuration.Builder(null)
                .startTime(now)
                .expiredTime(now.plusDays(1))
                .build();
    }


    @Test
    void testNullExpiredTime() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new TaskDuration.Builder(new ActivityDuration(now, now.plusDays(1)))
                    .startTime(now)
                    .expiredTime(null)
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "任务时间有效");
            Assert.hasText(e.getMessage(), "任务的结束时间不能为空");
        }
    }

    @Test
    void testStartTimeBeforeActivityStart() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new TaskDuration.Builder(new ActivityDuration(null, null))
                    .startTime(now)
                    .expiredTime(now.plusDays(1))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "任务时间有效");
            Assert.hasText(e.getMessage(), "任务的开始时间不能早于活动的开始时间");
        }
    }

    @Test
    void testExpiredTimeAfterActivityEnd() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new TaskDuration.Builder(new ActivityDuration(now.minusDays(1), now.plusDays(1)))
                    .startTime(now.minusDays(2))
                    .expiredTime(now.plusDays(1))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "任务时间有效");
            Assert.hasText(e.getMessage(), "任务的结束时间不能晚于活动的结束时间");
        }
    }

}
