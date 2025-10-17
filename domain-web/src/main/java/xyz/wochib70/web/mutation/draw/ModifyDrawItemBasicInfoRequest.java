package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawItemBasicInfoCmd;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "修改抽奖物品基本信息请求")
public class ModifyDrawItemBasicInfoRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotNull
    @Schema(description = "奖项ID", example = "1")
    private Long awardId;
    
    @NotBlank
    @Schema(description = "抽奖物品名称", example = "iPhone 15")
    private String name;
    
    @NotBlank
    @Schema(description = "抽奖物品描述", example = "最新款iPhone手机")
    private String description;

    public ModifyDrawItemBasicInfoCmd toCmd() {
        return new ModifyDrawItemBasicInfoCmd(
                new DefaultIdentifierId<>(drawPoolId),
                new DefaultIdentifierId<>(awardId),
                name,
                description
        );
    }
}