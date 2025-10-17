package xyz.wochib70.web.mutation.redeem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.redeem.cmd.DeletedRedeemCmd;

@Data
@Schema(description = "删除兑换请求")
public class DeletedRedeemRequest {

    @NotNull
    @Schema(description = "兑换ID", example = "1")
    private Long redeemId;

    public DeletedRedeemCmd toCmd() {
        return new DeletedRedeemCmd(new DefaultIdentifierId<>(redeemId));
    }
}