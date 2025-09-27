package xyz.wochib70.domain.activity;

import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class ActivityDomainRegistry {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        Objects.requireNonNull(applicationContext, "applicationContext不能为null");
        ActivityDomainRegistry.applicationContext = applicationContext;
    }

    public static ActivityRepository activityRepository() {
        Objects.requireNonNull(applicationContext, "applicationContext不能为null, 请ActivityDomainRegistry配置Spring上下文");
        ActivityRepository bean = applicationContext.getBean(ActivityRepository.class);
        return Objects.requireNonNull(bean, "ActivityRepository不能为null, 请检查Spring配置");
    }
}
