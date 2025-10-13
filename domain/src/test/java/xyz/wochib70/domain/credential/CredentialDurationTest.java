package xyz.wochib70.domain.credential;


import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import xyz.wochib70.domain.activity.ActivityDuration;

import java.time.LocalDateTime;

class CredentialDurationTest {


    @Test
    void testValid() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDuration activityDuration = new ActivityDuration(
                now.minusDays(1),
                now.plusDays(1)
        );
        CredentialDuration duration = new CredentialDuration.Builder(activityDuration)
                .startTime(now.minusDays(1))
                .expiredTime(now.plusDays(1))
                .build();
        Assert.isTrue(duration.valid(), "凭证时间有效");
    }

    @Test
    void testInvalid() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDuration activityDuration = new ActivityDuration(
                now.minusDays(1),
                now
        );
        CredentialDuration duration = new CredentialDuration.Builder(activityDuration)
                .startTime(now.minusDays(1))
                .expiredTime(now)
                .build();
        Assert.isTrue(!duration.valid(), "凭证时间无效");
    }

    @Test
    void testNullActivityDuration() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new CredentialDuration.Builder(null)
                    .startTime(now)
                    .expiredTime(now.plusDays(1))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "凭证时间有效");
            Assert.hasText(e.getMessage(), "ActivityDuration不能为null");
        }
    }



    @Test
    void testNullExpiredTime() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new CredentialDuration.Builder(new ActivityDuration(now, now.plusDays(1)))
                    .startTime(now)
                    .expiredTime(now.plusDays(2))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "凭证时间有效");
            Assert.hasText(e.getMessage(), "凭证的结束时间不能晚于活动的结束时间");
        }
    }

    @Test
    void testStartTimeBeforeActivityStart() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(now)
                    .expiredTime(now.plusDays(1))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "凭证时间有效");
            Assert.hasText(e.getMessage(), "凭证的开始时间不能早于活动的开始时间");
        }
    }

    @Test
    void testExpiredTimeAfterActivityEnd() {
        try {
            LocalDateTime now = LocalDateTime.now();
            new CredentialDuration.Builder(new ActivityDuration(now.minusDays(1), now.plusDays(1)))
                    .startTime(now.minusDays(2))
                    .expiredTime(now.plusDays(1))
                    .build();
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "凭证时间有效");
            Assert.hasText(e.getMessage(), "凭证的结束时间不能晚于活动的结束时间");
        }
    }

}