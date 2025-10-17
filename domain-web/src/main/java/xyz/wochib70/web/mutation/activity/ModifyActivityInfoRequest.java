package xyz.wochib70.web.mutation.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.activity.cmd.ModifyActivityInfoCmd;
import xyz.wochib70.domain.activity.ActivityInfo;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "修改活动信息请求")
public class ModifyActivityInfoRequest {

    @NotNull
    @Schema(description = "活动ID", example = "1")
    private Long activityId;
    
    @NotNull
    @Schema(description = "活动名称", example = "双十一促销活动")
    private String name;
    
    @Schema(description = "活动描述", example = "双十一期间全场商品8折优惠")
    private String description;
    
    @Schema(description = "活动图片列表", example = "[\"image1.jpg\", \"image2.jpg\"]")
    private List<String> images;

    public ModifyActivityInfoCmd toCmd() {
        ActivityInfo info = new ActivityInfo(name, description, images);
        return new ModifyActivityInfoCmd(new DefaultIdentifierId<>(activityId), info);
    }
}