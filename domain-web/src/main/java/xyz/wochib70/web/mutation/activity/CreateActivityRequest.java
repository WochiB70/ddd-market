package xyz.wochib70.web.mutation.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import xyz.wochib70.domain.activity.*;
import xyz.wochib70.domain.activity.cmd.CreateActivityCmd;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "创建活动请求")
public class CreateActivityRequest {

    @NotNull
    @Schema(description = "活动名称", example = "春节抽奖活动")
    private String name;
    
    @Schema(description = "活动描述", example = "春节期间举行的抽奖活动")
    private String description;
    
    @Schema(description = "活动图片列表", example = "[\"image1.jpg\", \"image2.jpg\"]")
    private List<String> images;
    
    @Schema(description = "活动开始时间", example = "2024-01-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @Schema(description = "活动结束时间", example = "2024-01-31T23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    @NotNull
    @Schema(description = "参与次数限制类型", example = "DAY_COUNT")
    private CountLimitType countLimitType;
    
    @Schema(description = "参与次数限制数量", example = "3")
    private Integer countLimit;
    
    @NotNull
    @Schema(description = "是否需要凭证", example = "true")
    private Boolean credentialLimit;
    
    @NotNull
    @Schema(description = "奖励类型", example = "DRAW")
    private ActivityAwardType awardType;

    public CreateActivityCmd toCmd() {
        ActivityInfo info = new ActivityInfo(name, description, images);
        ActivityDuration duration = new ActivityDuration(startTime, endTime);
        ActivityCountLimit countLimit = new ActivityCountLimit(countLimitType, this.countLimit);
        return new CreateActivityCmd(info, duration, countLimit, credentialLimit, awardType);
    }
}