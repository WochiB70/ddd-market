package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.cmd.StartActivityCmd;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "开始活动请求")
public class StartActivityRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;

    public StartActivityCmd toCmd() {
        return new StartActivityCmd(new DefaultIdentifierId<>(activityId));
    }
}