package xyz.wochib70.domain.task;

import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class TaskDomainRegistry {

    private static ApplicationContext applicationContext;

    private TaskDomainRegistry() {
        throw new UnsupportedOperationException("这是一个工具类，不允许实例化");
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        TaskDomainRegistry.applicationContext = Objects.requireNonNull(applicationContext, "applicationContext不能为null");
    }

    public static TaskRepository taskRepository() {
        Objects.requireNonNull(applicationContext, "applicationContext不能为null, 需要手动配置一个Spring容器的实例到TaskDomainRegistry中");
        TaskRepository bean = applicationContext.getBean(TaskRepository.class);
        return Objects.requireNonNull(bean, "taskRepository不能为null，需要手动设置一个实例到Spring容器中");
    }

}
