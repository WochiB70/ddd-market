package xyz.wochib70.domain;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicLong;

public class AggregateTestBase {

    @BeforeAll
    static void setUp() {

        DomainIdRegistry.domainEventIdGenerator = (DomainEventIdGenerator<Long>) Mockito.mock(DomainEventIdGenerator.class);
        Mockito.when(DomainIdRegistry.domainEventIdGenerator.nextEventId())
                .thenReturn(new DefaultIdentifierId<>(new AtomicLong(0L).incrementAndGet()));

    }
}
