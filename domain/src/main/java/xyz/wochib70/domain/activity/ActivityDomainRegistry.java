package xyz.wochib70.domain.activity;

import org.springframework.context.ApplicationContext;
import xyz.wochib70.domain.utils.ParameterUtil;

public class ActivityDomainRegistry {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null");
        ActivityDomainRegistry.applicationContext = applicationContext;
    }

    public static ActivityRepository activityRepository() {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null, 请ActivityDomainRegistry配置Spring上下文");
        ActivityRepository bean = applicationContext.getBean(ActivityRepository.class);
        ParameterUtil.requireNonNull(bean, "ActivityRepository不能为null, 请检查Spring配置");
        return bean;
    }
}
