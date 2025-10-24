package xyz.wochib70.web.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.ActivityStatus;

import java.time.LocalDateTime;

@Data
@Schema(description = "活动卡片信息响应")
public class QueryActivityCardsResponse {

    @Schema(description = "活动ID")
    private Long id;

    @Schema(description = "活动名称")
    private String name;

    @Schema(description = "活动时间范围")
    private Duration durationDetail;

    @Schema(description = "活动描述")
    private String description;

    @Schema(description = "活动图片")
    private String image;

    @Schema(description = "参与人数")
    private Long participantCount;

    @Schema(description = "活动状态")
    private ActivityStatus status;

    @Schema(description = "奖励类型")
    private ActivityAwardType awardType;


    public record Duration(
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime startTime,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime endTime
    ){}
}