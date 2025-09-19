package xyz.wochib70.domain.activity;

import org.springframework.context.ApplicationContext;

public class ActivityDomainRegistry {

    public static ApplicationContext applicationContext;

    public static ActivityRepository activityRepository() {
        return applicationContext.getBean(ActivityRepository.class);
    }
}
