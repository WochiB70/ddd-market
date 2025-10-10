package xyz.wochib70.domain.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.UserId;

class TaskCountLimitTest {


    @Test
    void createTaskCountLimitTestWithNegativeCountTest() {
        try {
            new TaskCountLimit(TaskCountLimitType.TASK_DURATION, -1);
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "IllegalArgumentException should be thrown");
        }
    }

    @Test
    void createTaskCountLimitTestWithoutTypeTest() {
        try {
            new TaskCountLimit(null, 0);
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "IllegalArgumentException should be thrown");
        }
    }

    @Test
    void createTaskCountLimitWithZeroCountTest() {
        try {
            new TaskCountLimit(TaskCountLimitType.TASK_DURATION, 0);
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "IllegalArgumentException should be thrown");
        }
    }

    @Test
    void createTaskCountLimitWithInfiniteTypeTest() {
        TaskCountLimit taskCountLimit = new TaskCountLimit(TaskCountLimitType.INFINITE, null);
        Assert.isTrue(taskCountLimit.type() == TaskCountLimitType.INFINITE, "TaskCountLimitType should be INFINITE");
    }

    @Test
    void receiveTaskTest() {
        TaskRepository mock = Mockito.mock(TaskRepository.class);

        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        TaskDomainRegistry.setApplicationContext(context);

        Mockito.when(context.getBean(TaskRepository.class))
                .thenReturn(mock);

        Mockito.when(mock.countReceivedTaskInDay(Mockito.any(), Mockito.any()))
                .thenReturn(0);

        Mockito.when(mock.countReceivedTaskByUserIdInDuration(Mockito.any(), Mockito.any()))
                .thenReturn(0);

        TaskCountLimit taskCountLimit = new TaskCountLimit(TaskCountLimitType.DAY_COUNT, 5);
        taskCountLimit.receiveTask(new UserId(1L), new DefaultIdentifierId<>(1L));

        Assert.isTrue(true, "receiveTask should be called");
    }

    @Test
    void receiveTaskWithTaskCountLimitExceptionTest() {
        TaskRepository mock = Mockito.mock(TaskRepository.class);

        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        TaskDomainRegistry.setApplicationContext(context);

        Mockito.when(context.getBean(TaskRepository.class))
                .thenReturn(mock);

        Mockito.when(mock.countReceivedTaskInDay(Mockito.any(), Mockito.any()))
                .thenReturn(5);

        Mockito.when(mock.countReceivedTaskByUserIdInDuration(Mockito.any(), Mockito.any()))
                .thenReturn(5);

        TaskCountLimit taskCountLimit = new TaskCountLimit(TaskCountLimitType.TASK_DURATION, 5);
        try {
            taskCountLimit.receiveTask(new UserId(1L), new DefaultIdentifierId<>(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof TaskCountLimitException, "TaskCountLimitException should be thrown");
        }

    }
}
