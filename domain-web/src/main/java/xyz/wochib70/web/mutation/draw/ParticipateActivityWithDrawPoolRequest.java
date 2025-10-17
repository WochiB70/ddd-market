package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.cmd.ParticipateActivityWithDrawPoolCmd;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "参与活动抽奖请求")
public class ParticipateActivityWithDrawPoolRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    
    @NotBlank
    @Schema(description = "凭证使用码", example = "CREDENTIAL_123")
    private String credentialUsageCode;

    public ParticipateActivityWithDrawPoolCmd toCmd() {
        return new ParticipateActivityWithDrawPoolCmd(
                new DefaultIdentifierId<>(activityId),
                new DefaultIdentifierId<>(drawPoolId),
                new UserId(userId),
                credentialUsageCode
        );
    }
}