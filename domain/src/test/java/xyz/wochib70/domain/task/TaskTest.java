package xyz.wochib70.domain.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.*;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.task.events.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest extends AggregateTestBase {

    private TaskImpl task;
    private IdentifierId<Long> taskId;
    private IdentifierId<Long> activityId;

    @BeforeEach
    void setUp() {
        taskId = new DefaultIdentifierId<>(1L);
        activityId = new DefaultIdentifierId<>(10L);
        task = new TaskImpl(taskId);
        task.setActivityId(activityId);
        task.setStatus(TaskStatus.ENABLE);
        task.setInfo(new TaskInfo("测试任务", "测试任务描述"));

        TaskCountLimit countLimit = new TaskCountLimit(TaskCountLimitType.INFINITE, null);
        task.setTaskCountLimit(countLimit);

        LocalDateTime now = LocalDateTime.now();
        TaskDuration duration = new TaskDuration.Builder(new ActivityDuration(now.minusDays(2), now.plusDays(2)))
                .startTime(now.minusDays(1))
                .expiredTime(now.plusDays(1))
                .build();
        task.setDuration(duration);

        task.setReceivedTaskExpireTime(new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_TODAY_END, null));
        task.setTaskAward(new TaskAward(TaskAwardType.CREDENTIALS, new DefaultIdentifierId<>(1L), 10));
        task.setCompleteEvent(CompleteEvent.USER_LOGIN_PER_DAY);
    }

    @Test
    void receive_shouldThrowException_whenUserIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.receive(null);
        });
        assertEquals("用户不能为空", exception.getMessage());
    }

    @Test
    void receive_shouldThrowException_whenTaskIsExpired() {
        TaskDuration expiredDuration = new TaskDuration(LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(3));
        task.setDuration(expiredDuration);

        TaskCountLimitException exception = assertThrows(TaskCountLimitException.class, () -> {
            task.receive(new UserId(1L));
        });
        assertEquals("任务已过期", exception.getMessage());
    }

    @Test
    void receive_shouldThrowException_whenTaskIsNotEnabled() {
        task.setStatus(TaskStatus.UNABLE);

        TaskCannotClaimedException exception = assertThrows(TaskCannotClaimedException.class, () -> {
            task.receive(new UserId(1L));
        });
        assertEquals("任务不能被领取", exception.getMessage());
    }

    @Test
    void receive_shouldPublishTaskReceivedEvent_whenSuccessful() {
        UserId userId = new UserId(1L);
        task.setTaskCountLimit(new TaskCountLimit(TaskCountLimitType.INFINITE, null));
        task.receive(userId);

        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskReceivedEvent.class, events.getFirst());
        TaskReceivedEvent event = (TaskReceivedEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
        assertEquals(userId, event.getUserId());
    }

    @Test
    void disable_shouldChangeStatusAndPublishEvent_whenTaskIsNotAlreadyDisabled() {
        task.disable();

        assertEquals(TaskStatus.UNABLE, task.getStatus());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskDisabledEvent.class, events.getFirst());
        TaskDisabledEvent event = (TaskDisabledEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
    }

    @Test
    void disable_shouldNotPublishEvent_whenTaskIsAlreadyDisabled() {
        task.setStatus(TaskStatus.UNABLE);
        task.disable();

        assertEquals(TaskStatus.UNABLE, task.getStatus());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void enable_shouldChangeStatusAndPublishEvent_whenTaskIsNotAlreadyEnabled() {
        task.setStatus(TaskStatus.UNABLE);
        task.enable();

        assertEquals(TaskStatus.ENABLE, task.getStatus());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskEnabledEvent.class, events.getFirst());
        TaskEnabledEvent event = (TaskEnabledEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
    }

    @Test
    void enable_shouldNotPublishEvent_whenTaskIsAlreadyEnabled() {
        task.enable();

        assertEquals(TaskStatus.ENABLE, task.getStatus());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void modifyInfo_shouldThrowException_whenInfoIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.modifyInfo(null);
        });
        assertEquals("任务信息不能为空", exception.getMessage());
    }

    @Test
    void modifyInfo_shouldChangeInfoAndPublishEvent_whenInfoIsDifferent() {
        TaskInfo newInfo = new TaskInfo("新任务名称", "新任务描述");
        task.modifyInfo(newInfo);

        assertEquals(newInfo, task.getInfo());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskInfoModifiedEvent.class, events.getFirst());
        TaskInfoModifiedEvent event = (TaskInfoModifiedEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
        assertEquals(newInfo, event.getInfo());
    }

    @Test
    void modifyInfo_shouldNotPublishEvent_whenInfoIsSame() {
        TaskInfo sameInfo = new TaskInfo(task.getInfo().name(), task.getInfo().description());
        task.modifyInfo(sameInfo);

        assertEquals(sameInfo, task.getInfo());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void modifyCompleteEvent_shouldThrowException_whenCompleteEventIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.modifyCompleteEvent(null);
        });
        assertEquals("任务完成事件不能为空", exception.getMessage());
    }

    @Test
    void modifyCompleteEvent_shouldChangeCompleteEventAndPublishEvent_whenCompleteEventIsDifferent() {
        CompleteEvent newEvent = CompleteEvent.ACTIVITY_SHARED;
        task.modifyCompleteEvent(newEvent);

        assertEquals(newEvent, task.getCompleteEvent());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskCompleteEventModifiedEvent.class, events.getFirst());
        TaskCompleteEventModifiedEvent event = (TaskCompleteEventModifiedEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
        assertEquals(newEvent, event.getCompleteEvent());
    }

    @Test
    void modifyCompleteEvent_shouldNotPublishEvent_whenCompleteEventIsSame() {
        task.modifyCompleteEvent(CompleteEvent.USER_LOGIN_PER_DAY);

        assertEquals(CompleteEvent.USER_LOGIN_PER_DAY, task.getCompleteEvent());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void modifyCountLimit_shouldThrowException_whenTaskCountLimitIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.modifyCountLimit(null);
        });
        assertEquals("任务限制不能为空", exception.getMessage());
    }

    @Test
    void modifyCountLimit_shouldChangeCountLimitAndPublishEvent_whenCountLimitIsDifferent() {
        TaskCountLimit newLimit = new TaskCountLimit(TaskCountLimitType.DAY_COUNT, 5);
        task.modifyCountLimit(newLimit);

        assertEquals(newLimit, task.getTaskCountLimit());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskCountLimitModifiedEvent.class, events.getFirst());
        TaskCountLimitModifiedEvent event = (TaskCountLimitModifiedEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
        assertEquals(newLimit, event.getTaskCountLimit());
    }

    @Test
    void modifyCountLimit_shouldNotPublishEvent_whenCountLimitIsSame() {
        TaskCountLimit sameLimit = new TaskCountLimit(task.getTaskCountLimit().type(), task.getTaskCountLimit().count());
        task.modifyCountLimit(sameLimit);

        assertEquals(sameLimit, task.getTaskCountLimit());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void modifyReceivedTaskExpireTime_shouldThrowException_whenReceivedTaskExpireTimeIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.modifyReceivedTaskExpireTime(null);
        });
        assertEquals("任务领取的过期时间不能为空", exception.getMessage());
    }

    @Test
    void modifyReceivedTaskExpireTime_shouldChangeExpireTimeAndPublishEvent_whenExpireTimeIsDifferent() {
        ReceivedTaskExpireTime newExpireTime = new ReceivedTaskExpireTime(ReceivedTaskExpireTimeType.EXPIRE_IN_TIME, 3600L);
        task.modifyReceivedTaskExpireTime(newExpireTime);

        assertEquals(newExpireTime, task.getReceivedTaskExpireTime());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskReceivedTaskExpireTimeModifiedEvent.class, events.getFirst());
        TaskReceivedTaskExpireTimeModifiedEvent event = (TaskReceivedTaskExpireTimeModifiedEvent) events.getFirst();
        assertEquals(activityId, event.getTaskId()); // 注意这里使用的是activityId
        assertEquals(newExpireTime, event.getReceivedTaskExpireTime());
    }

    @Test
    void modifyReceivedTaskExpireTime_shouldNotPublishEvent_whenExpireTimeIsSame() {
        ReceivedTaskExpireTime sameExpireTime = new ReceivedTaskExpireTime(
                task.getReceivedTaskExpireTime().type(),
                task.getReceivedTaskExpireTime().seconds()
        );
        task.modifyReceivedTaskExpireTime(sameExpireTime);

        assertEquals(sameExpireTime, task.getReceivedTaskExpireTime());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }

    @Test
    void modifyTaskAward_shouldThrowException_whenTaskAwardIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            task.modifyTaskAward(null);
        });
        assertEquals("任务奖励不能为空", exception.getMessage());
    }

    @Test
    void modifyTaskAward_shouldChangeTaskAwardAndPublishEvent_whenTaskAwardIsDifferent() {
        TaskAward newAward = new TaskAward(TaskAwardType.CURRENCY_POINTS, new DefaultIdentifierId<>(2L), 100);
        task.modifyTaskAward(newAward);

        assertEquals(newAward, task.getTaskAward());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(1, events.size());
        assertInstanceOf(TaskAwardModifiedEvent.class, events.getFirst());
        TaskAwardModifiedEvent event = (TaskAwardModifiedEvent) events.getFirst();
        assertEquals(taskId, event.getTaskId());
        assertEquals(newAward, event.getTaskAward());
    }

    @Test
    void modifyTaskAward_shouldNotPublishEvent_whenTaskAwardIsSame() {
        TaskAward sameAward = new TaskAward(task.getTaskAward().type(), task.getTaskAward().awardId(), 10);
        task.modifyTaskAward(sameAward);

        assertEquals(sameAward, task.getTaskAward());
        List<? super AggregateEvent<Long, Long>> events = task.getEvents();
        assertEquals(0, events.size());
    }
}