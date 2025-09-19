package xyz.wochib70.domain.activity;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

public sealed interface Activity extends Aggregate<Long, Long> permits ActivityImpl {

    IdentifierId<Long> getActivityId();


    boolean useCredentialLimit();

    void publish();

    void close();

    void start();

    void participate(UserId userId);

    void modifyActivityInfo(ActivityInfo info);

    void modifyDuration(ActivityDuration duration);

    void modifyCountLimit(CountLimit countLimit);

    void modifyCredentialLimit(Boolean credentialLimit);

    void modifyAwardType(ActivityAwardType awardType);
}
