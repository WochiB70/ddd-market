package xyz.wochib70.domain.activity;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.DomainEventIdGenerator;
import xyz.wochib70.domain.DomainIdRegistry;

import java.util.concurrent.atomic.AtomicLong;

public class ActivityTestBase {

    @MockitoBean
    protected DomainEventIdGenerator<Long> eventIdGenerator;

    @MockitoBean
    protected ActivityRepository repository;

    @MockitoBean
    protected ActivityIdGenerator idGenerator;

    @BeforeEach
    public void baseInit() {
        DomainIdRegistry.domainEventIdGenerator = eventIdGenerator;
        Mockito.when(eventIdGenerator.nextEventId())
                .thenReturn(new DefaultIdentifierId<>(new AtomicLong(0L).incrementAndGet()));

        Mockito.doNothing()
                .when(repository)
                .update(Mockito.any());

        AtomicLong id = new AtomicLong(0L);
        Mockito.when(idGenerator.nextAggregateId())
                .thenReturn(new DefaultIdentifierId<>(id.incrementAndGet()));
    }
}
