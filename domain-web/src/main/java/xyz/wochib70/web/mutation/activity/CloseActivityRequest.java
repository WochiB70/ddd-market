package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.cmd.CloseActivityCmd;

@Data
@Schema(description = "关闭活动请求")
public class CloseActivityRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;

    public CloseActivityCmd toCmd() {
        return new CloseActivityCmd(new DefaultIdentifierId<>(activityId));
    }
}