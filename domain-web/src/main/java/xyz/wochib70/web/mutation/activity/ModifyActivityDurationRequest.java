package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.ActivityDuration;
import xyz.wochib70.domain.activity.cmd.ModifyActivityDurationCmd;

import java.time.LocalDateTime;

@Data
@Schema(description = "修改活动时间请求")
public class ModifyActivityDurationRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @Schema(description = "活动开始时间", example = "2025-11-01T00:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "活动结束时间", example = "2025-11-11T23:59:59")
    private LocalDateTime endTime;

    public ModifyActivityDurationCmd toCmd() {
        ActivityDuration duration = new ActivityDuration(startTime, endTime);
        return new ModifyActivityDurationCmd(new DefaultIdentifierId<>(activityId), duration);
    }
}