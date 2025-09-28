package xyz.wochib70.domain.credential;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.ActivityDuration;

import java.time.LocalDateTime;

public class CredentialFailTest extends AggregateTestBase {

    @Test
    void createCredentialWithoutDurationFailTest() {

        try {
            DefaultIdentifierId<Long> id = new DefaultIdentifierId<>(0L);
            CredentialIdGenerator credentialIdGenerator = Mockito.mock(CredentialIdGenerator.class);
            Mockito.when(credentialIdGenerator.nextAggregateId())
                    .thenReturn(id);


            CredentialFactory factory = new CredentialFactory(credentialIdGenerator);


            factory.create(null, 1, new UserId(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "凭证创建失败");
            Assert.hasText(e.getMessage(), "有效期不能为null");
        }
    }

    @Test
    void createCredentialWithoutUserIdFailTest() {
        try {
            DefaultIdentifierId<Long> id = new DefaultIdentifierId<>(0L);
            CredentialIdGenerator credentialIdGenerator = Mockito.mock(CredentialIdGenerator.class);
            Mockito.when(credentialIdGenerator.nextAggregateId())
                    .thenReturn(id);


            CredentialFactory factory = new CredentialFactory(credentialIdGenerator);
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now())
                    .expiredTime(LocalDateTime.now().plusDays(1))
                    .build();

            factory.create(duration, 1, null);
        } catch (Exception e) {
            Assert.isTrue(e instanceof NullPointerException, "凭证创建失败");
            Assert.hasText(e.getMessage(), "用户Id不能为null");
        }
    }

    @Test
    void createCredentialWithLessOneCountFailTest() {
        try {
            DefaultIdentifierId<Long> id = new DefaultIdentifierId<>(0L);
            CredentialIdGenerator credentialIdGenerator = Mockito.mock(CredentialIdGenerator.class);
            Mockito.when(credentialIdGenerator.nextAggregateId())
                    .thenReturn(id);


            CredentialFactory factory = new CredentialFactory(credentialIdGenerator);
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now())
                    .expiredTime(LocalDateTime.now().plusDays(1))
                    .build();

            factory.create(duration, -1, new UserId(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "凭证创建失败");
            Assert.hasText(e.getMessage(), "使用次数不能小于1");
        }
    }

    @Test
    void participateWithOtherUserFailTest() {
        try {
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now())
                    .expiredTime(LocalDateTime.now().plusDays(1))
                    .build();

            CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
            credential.setUser(new UserId(1L));
            credential.setStatus(CredentialStatus.VALID);
            credential.setUnusedCount(1);
            credential.setDuration(duration);

            credential.participate(new UserId(2L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalCredentialException, "非法的凭证");
        }
    }

    @Test
    void participateWithExpiredCredentialFailTest() {
        try {
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now().minusDays(4))
                    .expiredTime(LocalDateTime.now().minusDays(2))
                    .build();

            CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
            credential.setUser(new UserId(1L));
            credential.setStatus(CredentialStatus.VALID);
            credential.setUnusedCount(1);
            credential.setDuration(duration);

            credential.participate(new UserId(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof CredentialExpiredTimeInvalidException, "非法的凭证");
        }
    }

    @Test
    void participateWithUsedCredentialFailTest() {
        try {
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now().minusDays(1))
                    .expiredTime(LocalDateTime.now().plusDays(1))
                    .build();
            CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
            credential.setUser(new UserId(1L));
            credential.setStatus(CredentialStatus.VALID);
            credential.setUnusedCount(0);
            credential.setDuration(duration);
            credential.participate(new UserId(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof CredentialUnusedCountInvalidException, "非法的凭证");
        }
    }

    @Test
    void participateWithInvalidCredentialFailTest() {
        try {
            CredentialDuration duration = new CredentialDuration.Builder(new ActivityDuration(null, null))
                    .startTime(LocalDateTime.now().minusDays(1))
                    .expiredTime(LocalDateTime.now().plusDays(1))
                    .build();
            CredentialImpl credential = new CredentialImpl(new DefaultIdentifierId<>(0L));
            credential.setUser(new UserId(1L));
            credential.setStatus(CredentialStatus.INVALID);
            credential.setUnusedCount(1);
            credential.setDuration(duration);
            credential.participate(new UserId(1L));
        } catch (Exception e) {
            Assert.isTrue(e instanceof CredentialInvalidException, "非法的凭证");
        }
    }


}
