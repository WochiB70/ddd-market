package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemInventoryCmd;
import xyz.wochib70.domain.draw.DrawItemInventory;
import xyz.wochib70.domain.draw.DrawInventoryType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Data
@Schema(description = "修改抽奖物品库存请求")
public class ModifyDrawItemInventoryRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;
    
    @NotNull
    @Schema(description = "库存类型", example = "LIMITED")
    private DrawInventoryType inventoryType;
    
    @NotNull
    @Min(0)
    @Schema(description = "剩余库存数量", example = "100")
    private Integer surplus;

    public ModifyDrawItemInventoryCmd toCmd() {
        DrawItemInventory inventory = new DrawItemInventory(inventoryType, surplus);
        return new ModifyDrawItemInventoryCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                inventory
        );
    }
}