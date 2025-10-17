package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.cmd.ModifyActivityAwardTypeCmd;
import xyz.wochib70.domain.activity.ActivityAwardType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改活动奖励类型请求")
public class ModifyActivityAwardTypeRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "活动奖励类型", example = "CURRENCY")
    private ActivityAwardType awardType;

    public ModifyActivityAwardTypeCmd toCmd() {
        return new ModifyActivityAwardTypeCmd(new DefaultIdentifierId<>(activityId), awardType);
    }
}