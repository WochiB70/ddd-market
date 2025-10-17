package xyz.wochib70.web.mutation.draw;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.draw.cmd.ModifyDrawPoolNameCmd;

@Data
@Schema(description = "修改抽奖池名称请求")
public class ModifyDrawPoolNameRequest {

    @NotNull
    @Schema(description = "抽奖池ID", example = "1")
    private Long drawPoolId;
    
    @NotBlank
    @Schema(description = "抽奖池名称", example = "新年抽奖池")
    private String name;

    public ModifyDrawPoolNameCmd toCmd() {
        return new ModifyDrawPoolNameCmd(new DefaultIdentifierId<>(drawPoolId), name);
    }
}