package xyz.wochib70.web;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class QueryDslConfifuration {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        log.info("注入Query DSL 的查询工厂");
        return new JPAQueryFactory(entityManager);
    }
}
