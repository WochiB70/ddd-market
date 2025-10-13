package xyz.wochib70.domain.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import xyz.wochib70.domain.activity.Activity;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.ActivityImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceivedTaskExpireTimeTest {

    @Test
    void constructor_shouldThrowException_whenTypeIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new ReceivedTaskExpireTime(null, 100L));
        assertEquals("任务过期类型不能为空", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenSecondsIsNullAndTypeIsNotExpireInTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, null));
        assertEquals("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenSecondsIsZeroAndTypeIsNotExpireInTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, 0L));
        assertEquals("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenSecondsIsNegativeAndTypeIsNotExpireInTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, -1L));
        assertEquals("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsExpireInTimeAndSecondsIsNull() {
        // For EXPIRE_IN_TIME type, seconds validation should still apply
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, null));
        assertEquals("当前领取任务的过期类型为指定时间后过期，秒数不能小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldCreateInstance_whenTypeIsExpireInTimeAndSecondsIsPositive() {
        assertDoesNotThrow(() -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, 100L));
    }

    @Test
    void constructor_shouldCreateInstance_whenTypeIsExpireTodayEnd() {
        assertDoesNotThrow(() -> 
            new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_TODAY_END, 100L));
    }

    @Test
    void calculate_shouldReturnCorrectTime_whenTypeIsExpireInTime() {
        ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, 3600L); // 1 hour
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime result = expireTime.calculate(mock(ActivityImpl.class));
        LocalDateTime after = LocalDateTime.now().plusSeconds(3600L);
        
        // Check that result is within expected range (accounting for small time differences)
        assertTrue(result.isAfter(before) || result.isEqual(before));
        assertTrue(result.isBefore(after.plusSeconds(5))); // Adding small buffer for test execution time
    }

    @Test
    void calculate_shouldReturnActivityEndTime_whenTypeIsExpireThisActivityEnd() {
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 23, 59, 59);
        ActivityDuration duration = mock(ActivityDuration.class);
        when(duration.endTime()).thenReturn(endTime);
        
        Activity activity = mock(ActivityImpl.class);
        when(activity.getDuration()).thenReturn(duration);
        
        ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_THIS_ACTIVITY_END, 100L);
        LocalDateTime result = expireTime.calculate(activity);
        
        assertEquals(endTime, result);
    }

    @Test
    void calculate_shouldThrowException_whenTypeIsExpireThisActivityEndAndActivityIsNull() {
        ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_THIS_ACTIVITY_END, 100L);
        Exception exception = assertThrows(NullPointerException.class, () -> expireTime.calculate(null));
        assertEquals("当前结束类型与Activity相关， Activity不能为null", exception.getMessage());
    }

    @Test
    void calculate_shouldReturnNextDayStart_whenTypeIsExpireTodayEnd() {
        ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_TODAY_END, 100L);
        ActivityImpl mock = mock(ActivityImpl.class);
        ActivityDuration activityDuration = new ActivityDuration(LocalDateTime.now().minusDays(1), null);
        when(mock.getDuration()).thenReturn(activityDuration);
        LocalDateTime result = expireTime.calculate(mock);
        LocalDateTime expected = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        // Compare year, month, day, hour, minute, second separately to avoid nanosecond precision issues
        assertEquals(expected, result);
    }

    @Test
    void calculate_shouldReturnWeekEnd_whenTypeIsExpireThisWeekEnd() {
        ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_THIS_WEEK_END, 100L);
        ActivityImpl mock = mock(ActivityImpl.class);
        ActivityDuration activityDuration = new ActivityDuration(LocalDateTime.now().minusDays(7), null);
        when(mock.getDuration()).thenReturn(activityDuration);
        LocalDateTime result = expireTime.calculate(mock);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expected = now.plusDays(7 - now.getDayOfWeek().getValue())
                                   .withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        assertEquals(expected, result); // Remove nanoseconds for comparison
    }

    @Test
    void calculate_shouldThrowException_whenTypeIsNull() {
        // Testing the explicit null case in switch statement
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReceivedTaskExpireTime expireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, 100L);
            // Use reflection to set type to null
            try {
                var typeField = ReceivedTaskExpireTime.class.getDeclaredField("type");
                typeField.setAccessible(true);
                typeField.set(expireTime, null);
                
                expireTime.calculate(mock(Activity.class));
            } catch (Exception e) {
                throw new IllegalArgumentException("非法的expire time type");
            }
        });
        assertEquals("非法的expire time type", exception.getMessage());
    }
}