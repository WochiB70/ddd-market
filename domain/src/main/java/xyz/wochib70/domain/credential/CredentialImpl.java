package xyz.wochib70.domain.credential;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.events.*;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;

@Getter
@Setter
public non-sealed class CredentialImpl extends AbstractAggregate<Long> implements Credential {

    private CredentialStatus status;

    private CredentialDuration duration;

    private String usageCode;

    private int unusedCount;

    private UserId user;

    public CredentialImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getCredentialId() {
        return identifierId();
    }

    @Override
    public void participate(UserId userId) {

        if (!duration.valid()) {
            throw new CredentialExpiredTimeInvalidException("凭证已过期！" + usageCode);
        }
        if (unusedCount <= 0) {
            throw new CredentialUnusedCountInvalidException("凭证已使用完毕！" + usageCode);
        }
        if (!Objects.equals(userId, user)) {
            throw new IllegalCredentialException("非法的凭证" + usageCode);
        }

        switch (status) {
            case VALID -> {
                unusedCount--;
                publishEvent(new CredentialUsedEvent(
                        this.getCredentialId(),
                        usageCode,
                        userId
                ));
            }
            case EXPIRED -> throw new CredentialExpiredTimeInvalidException("凭证已过期！" + usageCode);
            case INVALID -> throw new CredentialInvalidException("凭证已失效！" + usageCode);
            case null, default -> throw new IllegalCredentialException("非法的凭证" + usageCode);
        }

    }


    @Override
    public void invalid() {
        if (!Objects.equals(status, CredentialStatus.INVALID)) {
            this.status = CredentialStatus.INVALID;
            publishEvent(new CredentialInvalidatedEvent(
                    getCredentialId()
            ));
        }
    }

    @Override
    public void valid() {
        if (!Objects.equals(status, CredentialStatus.VALID)) {
            this.status = CredentialStatus.VALID;
            publishEvent(new CredentialValidatedEvent(
                    getCredentialId()
            ));
        }
    }

    @Override
    public void modifyDuration(CredentialDuration duration) {
        ParameterUtil.requireNonNull(duration, "CredentialDuration不能为null");
        if (!Objects.equals(this.duration, duration)) {
            this.duration = duration;
            publishEvent(new CredentialDurationModifiedEvent(
                    getCredentialId(),
                    duration
            ));
        }
    }

    @Override
    public void modifyUnusedCount(int unusedCount) {
        ParameterUtil.requireExpression(unusedCount < 0, "不能设置小于0的次数");
        if (!Objects.equals(this.unusedCount, unusedCount)) {
            this.unusedCount = unusedCount;
            publishEvent(new CredentialUnsedCountModifiedEvent(
                    getCredentialId(),
                    unusedCount
            ));
        }
    }

}
