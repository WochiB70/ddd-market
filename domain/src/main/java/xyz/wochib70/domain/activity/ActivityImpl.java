package xyz.wochib70.domain.activity;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.events.*;

import java.util.Objects;

@Getter
@Setter
public non-sealed class ActivityImpl extends AbstractAggregate<Long> implements Activity {


    private ActivityInfo info;

    private ActivityDuration duration;

    private CountLimit countLimit;

    private Boolean credentialLimit;

    private ActivityStatus status;

    private ActivityAwardType awardType;

    public ActivityImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getActivityId() {
        return identifierId();
    }

    @Override
    public boolean useCredentialLimit() {
        return credentialLimit;
    }

    @Override
    public void publish() {
        switch (status) {
            case INIT -> {
                status = ActivityStatus.PUBLISHED;
                publishEvent(new ActivityPublishedEvent(
                        getActivityId()
                ));
            }
            case PUBLISHED -> {
            }
            case ACTIVE, CLOSE -> throw new ActivityStatusException("Activity已经开始或者被关闭不能再次发布");
        }
    }

    @Override
    public void close() {
        switch (status) {
            case INIT, PUBLISHED -> throw new ActivityStatusException("Activity未开始不能关闭");
            case ACTIVE -> {
                status = ActivityStatus.CLOSE;
                publishEvent(new ActivityClosedEvent(
                        getActivityId()
                ));
            }
            case CLOSE -> {
            }
        }
    }

    @Override
    public void start() {
        switch (status) {
            case INIT -> throw new ActivityStatusException("Activity未发布不能开始");
            case PUBLISHED, CLOSE -> {
                status = ActivityStatus.ACTIVE;
                publishEvent(new ActivityStartedEvent(
                        getActivityId()
                ));
            }
            case ACTIVE -> {
            }
        }
    }

    @Override
    public void participate(UserId userId) {
        countLimit.participate(userId);
    }

    @Override
    public void modifyActivityInfo(ActivityInfo info) {
        Objects.requireNonNull(info, "ActivityInfo不能为null");
        if (!Objects.equals(this.info, info)) {
            this.info = info;
            publishEvent(new ActivityInfoModifiedEvent(
                    getActivityId(),
                    info
            ));
        }
    }

    @Override
    public void modifyDuration(ActivityDuration duration) {
        Objects.requireNonNull(duration, "ActivityDuration不能为null");
        if (!Objects.equals(this.duration, duration)) {
            this.duration = duration;
            publishEvent(new ActivityDurationModifiedEvent(
                    getActivityId(),
                    duration
            ));
        }
    }

    @Override
    public void modifyCountLimit(CountLimit countLimit) {
        Objects.requireNonNull(countLimit, "CountLimit不能为null");
        if (!Objects.equals(this.countLimit, countLimit)) {
            this.countLimit = countLimit;
            publishEvent(new ActivityCountLimitModifiedEvent(
                    getActivityId(),
                    countLimit
            ));
        }
    }

    @Override
    public void modifyCredentialLimit(Boolean credentialLimit) {
        Objects.requireNonNull(credentialLimit, "CredentialLimit不能为null");
        if (!Objects.equals(this.credentialLimit, credentialLimit)) {
            this.credentialLimit = credentialLimit;
            publishEvent(new ActivityCredentialLimitModifiedEvent(
                    getActivityId(),
                    credentialLimit
            ));
        }
    }

    @Override
    public void modifyAwardType(ActivityAwardType awardType) {
        Objects.requireNonNull(awardType, "AwardType不能为null");
        if (!Objects.equals(this.awardType, awardType)) {
            this.awardType = awardType;
            publishEvent(new ActivityAwardTypeModifiedEvent(
                    getActivityId(),
                    awardType
            ));
        }
    }

    @Override
    public void create() {
        publishEvent(new ActivityCreatedEvent(
                getActivityId(),
                info,
                duration,
                countLimit,
                credentialLimit,
                awardType
        ));
    }
}
