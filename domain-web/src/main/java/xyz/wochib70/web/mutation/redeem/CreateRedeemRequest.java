package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.cmd.CreateRedeemCmd;

@Data
@Schema(description = "创建兑换请求")
public class CreateRedeemRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "兑换名称", example = "春节兑换活动")
    private String name;

    public CreateRedeemCmd toCmd() {
        return new CreateRedeemCmd(new DefaultIdentifierId<>(activityId), name);
    }
}