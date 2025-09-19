package xyz.wochib70.domain.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ActivityFactory {

    private final ActivityIdGenerator activityIdGenerator;


    public Activity createActivity(
            ActivityInfo info,
            ActivityDuration duration,
            CountLimit countLimit,
            Boolean credentialLimit,
            ActivityAwardType awardType
    ) {
        Objects.requireNonNull(info, "Activity基本信息不能为null");
        Objects.requireNonNull(duration, "Activity持续时间不能为null");
        Objects.requireNonNull(countLimit, "Activity人数限制不能为null");
        Objects.requireNonNull(credentialLimit, "Activity凭证限制不能为null");
        Objects.requireNonNull(awardType, "Activity奖品类型不能为null");
        var activityId = activityIdGenerator.nextAggregateId();
        var activity = new ActivityImpl(activityId);
        activity.modifyActivityInfo(info);
        activity.modifyDuration(duration);
        activity.modifyCountLimit(countLimit);
        activity.modifyCredentialLimit(credentialLimit);
        activity.setAwardType(awardType);
        activity.create();
        return activity;
    }

}
