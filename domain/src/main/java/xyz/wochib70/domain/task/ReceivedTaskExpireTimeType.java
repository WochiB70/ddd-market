package xyz.wochib70.domain.task;

public enum ReceivedTaskExpireTimeType {


    /**
     * 今天24：00过期
     */
    EXPIRE_TODAY_END,

    /**
     * 这周末24：00过期
     */
    EXPIRE_THIS_WEEK_END,

    /**
     * 活动到期时过期
     */
    EXPIRE_THIS_ACTIVITY_END,

    /**
     * 指定时间后过期
     */
    EXPIRE_IN_TIME,
}
