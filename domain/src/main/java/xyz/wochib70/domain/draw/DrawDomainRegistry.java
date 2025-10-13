package xyz.wochib70.domain.draw;

import org.springframework.context.ApplicationContext;
import xyz.wochib70.domain.utils.ParameterUtil;

public class DrawDomainRegistry {

    private static ApplicationContext applicationContext;

    private DrawDomainRegistry() {
        throw new UnsupportedOperationException("这是一个工具类不允许实例化");
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null");
        DrawDomainRegistry.applicationContext = applicationContext;
    }

    public static DrawItemIdGenerator awardIdGenerator() {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null, 需要手动配置一个Spring容器的实例到DrawDomainRegistry中");
        DrawItemIdGenerator bean = applicationContext.getBean(DrawItemIdGenerator.class);
        ParameterUtil.requireNonNull(bean, "awardIdGenerator不能为null，需要手动设置一个实例到Spring容器中");
        return bean;
    }
}
