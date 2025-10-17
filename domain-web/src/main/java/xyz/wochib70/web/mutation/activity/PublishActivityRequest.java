package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.activity.cmd.PublishActivityCmd;

@Data
@Schema(description = "发布活动请求")
public class PublishActivityRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;

    public PublishActivityCmd toCmd() {
        return new PublishActivityCmd(new DefaultIdentifierId<>(activityId));
    }
}