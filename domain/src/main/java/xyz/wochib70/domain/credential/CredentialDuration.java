package xyz.wochib70.domain.credential;

import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.utils.DurationUtil;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.time.LocalDateTime;
import java.util.Objects;


public record CredentialDuration(
        LocalDateTime startTime,
        LocalDateTime expiredTime
) {


    public boolean valid() {
        return DurationUtil.validTimeWithDuration(LocalDateTime.now(), startTime, expiredTime);
    }


    public static class Builder {


        private final ActivityDuration activityDuration;

        private LocalDateTime startTime;

        private LocalDateTime expiredTime;


        public Builder(ActivityDuration activityDuration) {
            ParameterUtil.requireNonNull(activityDuration, "ActivityDuration不能为空");
            this.activityDuration = activityDuration;
        }

        public Builder startTime(LocalDateTime startTime) {
            ParameterUtil.requireNonNull(startTime, "凭证的开始时间不能为空");
            this.startTime = startTime;
            return this;
        }

        public Builder expiredTime(LocalDateTime expiredTime) {
            ParameterUtil.requireNonNull(expiredTime, "凭证的结束时间不能为空");
            this.expiredTime = expiredTime;
            return this;
        }

        public CredentialDuration build() {
            validateWithActivityDuration(activityDuration, startTime, expiredTime);
            return new CredentialDuration(startTime, expiredTime);
        }


        private void validateWithActivityDuration(ActivityDuration activityDuration,
                                                  LocalDateTime startTime,
                                                  LocalDateTime expiredTime
        ) {
            LocalDateTime activityStart = activityDuration.startTime();
            LocalDateTime activityEnd = activityDuration.endTime();

            // 凭证的开始时间应该大于等于活动的开始时间
            if (activityStart.isAfter(startTime)) {
                throw new IllegalArgumentException("凭证的开始时间不能早于活动的开始时间");
            }

            // 凭证的结束时间应该小于等于活动的结束时间
            if (activityEnd.isBefore(expiredTime)) {
                throw new IllegalArgumentException("凭证的结束时间不能晚于活动的结束时间");
            }

            // 凭证的开始时间应该小于等于凭证的结束时间
            if (startTime.isAfter(expiredTime)) {
                throw new IllegalArgumentException("凭证的结束时间不能早于开始时间");
            }
        }
    }


}
