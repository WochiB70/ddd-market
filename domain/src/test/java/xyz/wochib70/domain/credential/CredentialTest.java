package xyz.wochib70.domain.credential;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.credential.events.CredentialInvalidatedEvent;
import xyz.wochib70.domain.credential.events.CredentialUsedEvent;
import xyz.wochib70.domain.credential.events.CredentialValidatedEvent;
import xyz.wochib70.domain.utils.DurationUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class CredentialTest extends AggregateTestBase {


    @Test
    void createCredential() {
        DefaultIdentifierId<Long> id = new DefaultIdentifierId<>(0L);
        CredentialIdGenerator credentialIdGenerator = Mockito.mock(CredentialIdGenerator.class);
        Mockito.when(credentialIdGenerator.nextCredentialId())
                .thenReturn(id);


        CredentialFactory factory = new CredentialFactory(credentialIdGenerator);

        CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(
                DurationUtil.MIN_TIME,
                DurationUtil.MAX_TIME))
                .startTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.now().plusDays(1))
                .build();

        Credential credential = factory.create(duration, 1, new UserId(1L));

        Assert.isTrue(Objects.equals(credential.getCredentialId(), id), "Id应该相等");
    }


    @Test
    void participate() {
        CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(
                LocalDateTime.of(1990, 1, 1, 0, 0, 0, 0),
                LocalDateTime.of(2999, 1, 1, 0, 0, 0, 0)))
                .startTime(LocalDateTime.now().minusDays(1))
                .expiredTime(LocalDateTime.now().plusDays(1))
                .build();

        CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
        credential.setUser(new UserId(1L));
        credential.setStatus(CredentialStatus.VALID);
        credential.setUnusedCount(1);
        credential.setDuration(duration);
        credential.participate(new UserId(1L));

        for (Object event : credential.getEvents()) {
            Assert.isTrue(event instanceof CredentialUsedEvent, "事件应该为CredentialUsedEvent");
            Assert.isTrue(Objects.equals(((CredentialUsedEvent) event).getCredentialId(), credential.getCredentialId()), "Id应该相等");
        }
    }

    @Test
    void invalid() {
        CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
        credential.setStatus(CredentialStatus.VALID);

        credential.invalid();

        for (Object event : credential.getEvents()) {
            Assert.isTrue(event instanceof CredentialInvalidatedEvent, "事件应该为CredentialInvalidatedEvent");
            Assert.isTrue(Objects.equals(((CredentialInvalidatedEvent) event).getCredentialId(), credential.getCredentialId()), "Id应该相等");
        }
    }

    @Test
    void valid() {
        CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
        credential.setStatus(CredentialStatus.INVALID);

        credential.valid();

        for (Object event : credential.getEvents()) {
            Assert.isTrue(event instanceof CredentialValidatedEvent, "事件应该为CredentialValidatedEvent");
            Assert.isTrue(Objects.equals(((CredentialValidatedEvent) event).getCredentialId(), credential.getCredentialId()), "Id应该相等");
        }
    }

}
