package xyz.wochib70.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.wochib70.domain.DomainEventIdGenerator;
import xyz.wochib70.domain.DomainIdRegistry;
import xyz.wochib70.domain.activity.ActivityDomainRegistry;
import xyz.wochib70.domain.draw.DrawDomainRegistry;
import xyz.wochib70.domain.redeem.RedeemDomainRegistry;
import xyz.wochib70.domain.task.TaskDomainRegistry;
import xyz.wochib70.infrastructure.DefaultIdGeneratorByDataBaseImpl;

@Slf4j
@AllArgsConstructor
@Configuration
public class DomainInitializer implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    private final DefaultIdGeneratorByDataBaseImpl idGeneratorByDataBase;

    @Override
    public void run(String... args) throws Exception {
        log.info("==================开始初始化Domain模块的静态注入====================");
        ActivityDomainRegistry.setApplicationContext(applicationContext);
        TaskDomainRegistry.setApplicationContext(applicationContext);
        RedeemDomainRegistry.setApplicationContext(applicationContext);
        DrawDomainRegistry.setApplicationContext(applicationContext);
        DomainIdRegistry.domainEventIdGenerator = domainEventIdGenerator();
        log.info("==================Domain模块的静态注入     完成====================");
    }


    @Bean
    public DomainEventIdGenerator<Long> domainEventIdGenerator() {
        return () -> idGeneratorByDataBase.nextAggregateId("domain_event");
    }
}
