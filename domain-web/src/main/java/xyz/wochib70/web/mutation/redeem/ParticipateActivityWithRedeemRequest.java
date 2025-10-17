package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.redeem.cmd.ParticipateActivityWithRedeemCmd;

@Data
@Schema(description = "参与活动兑换请求")
public class ParticipateActivityWithRedeemRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;

    @NotNull
    @Schema(description = "兑换商品ID", example = "1")
    private Long redeemItemId;

    @NotNull
    @Schema(description = "兑换数量", example = "2")
    private Integer count;

    @NotNull
    @Schema(description = "用户ID", example = "1001")
    private Long userId;

    @NotNull
    @Schema(description = "凭证使用码", example = "ABC123XYZ")
    private String credentialUsageCode;

    public ParticipateActivityWithRedeemCmd toCmd() {
        return new ParticipateActivityWithRedeemCmd(
                new DefaultIdentifierId<>(activityId),
                new DefaultIdentifierId<>(redeemId),
                new DefaultIdentifierId<>(redeemItemId),
                count,
                new UserId(userId),
                credentialUsageCode
        );
    }
}