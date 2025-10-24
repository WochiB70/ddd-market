package xyz.wochib70.web.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wochib70.domain.activity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "活动详情信息响应")
public class QueryActivityDetailResponse {


    @Schema(description = "活动ID")
    private Long id;

    @Schema(description = "活动基本信息")
    private ActivityInfo info;

    @Schema(description = "活动参与次数限制")
    private ActivityCountLimit countLimit;

    @Schema(description = "活动时间范围")
    private Duration duration;

    @Schema(description = "是否需要凭证限制")
    private Boolean credentialLimit;

    @Schema(description = "奖励类型")
    private ActivityAwardType awardType;

    @Schema(description = "任务数量")
    private Long taskNumber;

    public QueryActivityDetailResponse(
            Long id,
            String name,
            String description,
            List<String> images,
            LocalDateTime startTime,
            LocalDateTime endTime,
            CountLimitType countLimitType,
            Integer countLimit,
            Boolean credentialLimit,
            ActivityAwardType awardType,
            Long taskNumber
    ) {
        this.id = id;
        this.info = new ActivityInfo(name, description, images);
        this.duration = new Duration(startTime, endTime);
        this.countLimit = new ActivityCountLimit(countLimitType, countLimit);
        this.credentialLimit = credentialLimit;
        this.awardType = awardType;
        this.taskNumber = Objects.isNull(taskNumber) ? 0L : taskNumber;
    }

    public record Duration(
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime startTime,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime endTime
    ) {
    }
}