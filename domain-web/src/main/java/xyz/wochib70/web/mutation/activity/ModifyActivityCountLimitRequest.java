package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.ActivityCountLimit;
import xyz.wochib70.domain.activity.CountLimitType;
import xyz.wochib70.domain.activity.cmd.ModifyActivityCountLimitCmd;

@Data
@Schema(description = "修改活动次数限制请求")
public class ModifyActivityCountLimitRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "次数限制类型", example = "DAILY")
    private CountLimitType countLimitType;
    
    @Schema(description = "次数限制值", example = "3")
    private int countLimit;

    public ModifyActivityCountLimitCmd toCmd() {
        ActivityCountLimit countLimit = new ActivityCountLimit(countLimitType, this.countLimit);
        return new ModifyActivityCountLimitCmd(new DefaultIdentifierId<>(activityId), countLimit);
    }
}