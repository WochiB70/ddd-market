package xyz.wochib70.domain.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.utils.ParameterUtil;

@RequiredArgsConstructor
@Component
public class ActivityFactory {

    private final ActivityIdGenerator activityIdGenerator;


    public Activity createActivity(
            ActivityInfo info,
            ActivityDuration duration,
            ActivityCountLimit countLimit,
            Boolean credentialLimit,
            ActivityAwardType awardType
    ) {
        ParameterUtil.requireNonNull(info, "Activity基本信息不能为null");
        ParameterUtil.requireNonNull(duration, "Activity持续时间不能为null");
        ParameterUtil.requireNonNull(countLimit, "Activity人数限制不能为null");
        ParameterUtil.requireNonNull(credentialLimit, "Activity凭证限制不能为null");
        ParameterUtil.requireNonNull(awardType, "Activity奖品类型不能为null");
        var activityId = activityIdGenerator.nextActivityId();
        var activity = new ActivityImpl(activityId);
        activity.setInfo(info);
        activity.setDuration(duration);
        activity.setCountLimit(countLimit);
        activity.setCredentialLimit(credentialLimit);
        activity.setAwardType(awardType);
        activity.create();
        return activity;
    }

}
