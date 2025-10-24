package xyz.wochib70.web.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.wochib70.domain.task.*;

import java.time.LocalDateTime;

@Data
@Schema(description = "全局任务信息响应")
public class QueryGlobalTasksResponse {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "任务状态")
    private TaskStatus status;

    @Schema(description = "任务次数限制")
    private TaskCountLimit countLimit;

    @Schema(description = "任务开始时间")
    private LocalDateTime startTime;

    @Schema(description = "任务过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "任务完成事件")
    private CompleteEvent completeEvent;

    @Schema(description = "任务过期时间配置")
    private ReceivedTaskExpireTime receivedTaskExpireTime;

    @Schema(description = "任务奖励")
    private TaskAward award;


    @Schema(description = "任务奖励信息")
    public record TaskAward(
            @Schema(description = "奖励类型") TaskAwardType type,
            @Schema(description = "奖励ID") Long awardId,
            @Schema(description = "奖励数量") Integer count
    ) {

    }
}