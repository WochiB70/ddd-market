package xyz.wochib70.domain.usertask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.usertask.events.UserTaskCompletedEvent;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTaskTest extends AggregateTestBase {

    private UserTaskImpl userTask;
    private static final Long USER_TASK_ID = 1L;
    private static final Long TASK_ID = 2L;
    private static final Long USER_ID = 3L;

    @BeforeEach
    void setUp() {
        userTask = new UserTaskImpl(new DefaultIdentifierId<>(USER_TASK_ID));
        userTask.setTaskId(new DefaultIdentifierId<>(TASK_ID));
        userTask.setUserId(new UserId(USER_ID));
        userTask.setStatus(UserTaskStatus.PENDING);
    }

    @Test
    void complete_shouldMarkTaskAsCompletedAndPublishEvent_whenTaskIsNotExpired() {
        // Given
        userTask.setExpireTime(LocalDateTime.now().plusDays(1)); // 未过期

        // When
        userTask.complete();

        // Then
        assertEquals(UserTaskStatus.COMPLETED, userTask.getStatus());
        assertEquals(1, userTask.getEvents().size());
        assertInstanceOf(UserTaskCompletedEvent.class, userTask.getEvents().getFirst());
        
        UserTaskCompletedEvent event = (UserTaskCompletedEvent) userTask.getEvents().get(0);
        assertEquals(USER_TASK_ID, event.getUserTaskId().getId());
        assertEquals(TASK_ID, event.getTaskId().getId());
        assertEquals(USER_ID, event.getUserId().getId());
    }

    @Test
    void complete_shouldThrowException_whenTaskIsExpired() {
        // Given
        userTask.setExpireTime(LocalDateTime.now().minusDays(1)); // 已过期

        // When & Then
        UserTaskExpiredException exception = assertThrows(UserTaskExpiredException.class, () -> {
            userTask.complete();
        });
        
        assertEquals("任务已过期", exception.getMessage());
        assertEquals(UserTaskStatus.PENDING, userTask.getStatus()); // 状态未改变
        assertTrue(userTask.getEvents().isEmpty()); // 没有发布事件
    }

    @Test
    void complete_shouldNotPublishEvent_whenTaskAlreadyCompleted() {
        // Given
        userTask.setStatus(UserTaskStatus.COMPLETED);
        userTask.setExpireTime(LocalDateTime.now().plusDays(1));

        // When
        userTask.complete();

        // Then
        assertEquals(UserTaskStatus.COMPLETED, userTask.getStatus());
        assertTrue(userTask.getEvents().isEmpty()); // 没有发布新事件
    }

    @Test
    void getUserTaskId_shouldReturnCorrectId() {
        // When
        IdentifierId<Long> userTaskId = userTask.getUserTaskId();

        // Then
        assertEquals(USER_TASK_ID, userTaskId.getId());
    }
}