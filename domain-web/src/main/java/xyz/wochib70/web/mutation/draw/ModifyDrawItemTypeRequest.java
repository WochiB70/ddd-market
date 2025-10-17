package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemTypeCmd;
import xyz.wochib70.domain.draw.DrawItemType;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "修改抽奖物品类型请求")
public class ModifyDrawItemTypeRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;
    
    @NotNull
    @Schema(description = "抽奖物品类型", example = "PHYSICAL")
    private DrawItemType type;

    public ModifyDrawItemTypeCmd toCmd() {
        return new ModifyDrawItemTypeCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                type
        );
    }
}