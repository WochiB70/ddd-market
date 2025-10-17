package xyz.wochib70.web.mutation.credential;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.CredentialDuration;
import xyz.wochib70.domain.credential.cmd.CreateCredentialCmd;

import java.time.LocalDateTime;

@Data
@Schema(description = "创建凭证请求")
public class CreateCredentialRequest {

    @NotNull
    @Schema(description = "活动ID", example = "123")
    private Long activityId;
    
    @NotNull
    @Schema(description = "开始时间", example = "2023-01-01T00:00:00")
    private LocalDateTime startTime;
    
    @NotNull
    @Schema(description = "过期时间", example = "2023-12-31T23:59:59")
    private LocalDateTime expiredTime;
    
    @NotNull
    @Schema(description = "未使用次数", example = "10")
    private Integer unusedCount;
    
    @NotNull
    @Schema(description = "用户ID", example = "456")
    private Long userId;

    public CreateCredentialCmd toCmd() {
        CredentialDuration duration = new CredentialDuration(startTime, expiredTime);
        return new CreateCredentialCmd(new DefaultIdentifierId<>(activityId), duration, unusedCount, new UserId(userId));
    }
}