package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemInventoryTypeCmd;
import xyz.wochib70.domain.inventory.InventoryType;

@Data
@Schema(description = "修改抽奖物品库存请求")
public class ModifyDrawItemInventoryTypeRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;
    
    @NotNull
    @Schema(description = "库存类型", example = "LIMITED")
    private InventoryType  inventoryType;


    public ModifyDrawItemInventoryTypeCmd toCmd() {
        return new ModifyDrawItemInventoryTypeCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                inventoryType
        );
    }
}