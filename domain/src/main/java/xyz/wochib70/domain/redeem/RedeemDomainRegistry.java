package xyz.wochib70.domain.redeem;

import org.springframework.context.ApplicationContext;
import xyz.wochib70.domain.utils.ParameterUtil;

public class RedeemDomainRegistry {

    private static ApplicationContext applicationContext;

    private RedeemDomainRegistry() {
        throw new UnsupportedOperationException("此为工具类不可实例化");
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null");
        RedeemDomainRegistry.applicationContext = applicationContext;
    }

    public static RedeemItemIdGenerator redeemItemIdGenerator() {
        ParameterUtil.requireNonNull(applicationContext, "applicationContext不能为null, 需要手动配置一个Spring容器的实例到RedeemDomainRegistry中");
        RedeemItemIdGenerator bean = applicationContext.getBean(RedeemItemIdGenerator.class);
        ParameterUtil.requireNonNull(bean, "awardIdGenerator不能为null，需要手动设置一个实例到Spring容器中");
        return bean;
    }
}
