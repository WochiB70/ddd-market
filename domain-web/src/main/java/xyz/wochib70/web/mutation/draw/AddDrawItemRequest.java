package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.AddDrawItemCmd;
import xyz.wochib70.domain.draw.DrawItemInfo;
import xyz.wochib70.domain.draw.DrawItemType;
import xyz.wochib70.domain.draw.DrawItemInventory;
import xyz.wochib70.domain.draw.DrawInventoryType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Data
@Schema(description = "添加抽奖物品请求")
public class AddDrawItemRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotBlank
    @Schema(description = "抽奖物品名称", example = "iPhone 15")
    private String name;
    
    @Schema(description = "抽奖物品描述", example = "最新款iPhone手机")
    private String description;
    
    @NotNull
    @Schema(description = "抽奖物品类型", example = "PHYSICAL")
    private DrawItemType type;
    
    @NotNull
    @Min(1)
    @Schema(description = "抽奖物品权重", example = "10")
    private Integer weight;
    
    @NotNull
    @Schema(description = "库存类型", example = "LIMITED")
    private DrawInventoryType inventoryType;
    
    @NotNull
    @Min(0)
    @Schema(description = "剩余库存数量", example = "100")
    private Integer surplus;

    public AddDrawItemCmd toCmd() {
        DrawItemInventory inventory = new DrawItemInventory(inventoryType, surplus);
        DrawItemInfo drawItemInfo = new DrawItemInfo(name, description, type, weight, inventory);
        return new AddDrawItemCmd(new DefaultIdentifierId<>(drawPoolId), drawItemInfo);
    }
}