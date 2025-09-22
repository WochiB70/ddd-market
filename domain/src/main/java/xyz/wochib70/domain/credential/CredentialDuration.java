package xyz.wochib70.domain.credential;

import lombok.Getter;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.utils.DurationUtil;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class CredentialDuration {

    private final LocalDateTime startTime;

    private final LocalDateTime expiredTime;

    private CredentialDuration(
            LocalDateTime startTime,
            LocalDateTime expiredTime
    ) {
        this.startTime = startTime;
        this.expiredTime = expiredTime;
    }

    public boolean valid() {
        return DurationUtil.validTimeWithDuration(LocalDateTime.now(), startTime, expiredTime);
    }


    public static class Builder {


        private final ActivityDuration activityDuration;

        private LocalDateTime startTime;

        private LocalDateTime expiredTime;


        public Builder(ActivityDuration activityDuration) {
            Objects.requireNonNull(activityDuration, "ActivityDuration不能为null");
            this.activityDuration = activityDuration;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder expiredTime(LocalDateTime expiredTime) {
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

            // 情况1：活动无时间限制，凭证时间只要自身合理即可
            if (Objects.isNull(activityStart) && Objects.isNull(activityEnd)) {
                return;
            }

            // 情况2：活动只有开始时间，凭证需在其后开始
            if (Objects.isNull(activityEnd)) {
                Objects.requireNonNull(startTime, "活动具备开始时间，凭证的startTime不能为null");
                if (startTime.isBefore(activityStart)) {
                    throw new IllegalArgumentException("凭证的开始时间不能早于活动的开始时间");
                }
                return;
            }

            // 情况3：活动只有结束时间，凭证需在其前结束
            if (Objects.isNull(activityStart)) {
                Objects.requireNonNull(expiredTime, "活动具备结束时间，凭证的expiredTime不能为null");
                if (expiredTime.isAfter(activityEnd)) {
                    throw new IllegalArgumentException("凭证的结束时间不能晚于活动的结束时间");
                }
                return;
            }

            // 情况4：活动有明确的时间范围，凭证需在其范围内
            Objects.requireNonNull(startTime, "活动具备开始时间，凭证的startTime不能为null");
            Objects.requireNonNull(expiredTime, "活动具备结束时间，凭证的expiredTime不能为null");

            if (startTime.isBefore(activityStart)) {
                throw new IllegalArgumentException("凭证的开始时间不能早于活动的开始时间");
            }
            if (expiredTime.isAfter(activityEnd)) {
                throw new IllegalArgumentException("凭证的结束时间不能晚于活动的结束时间");
            }
        }
    }


}
