package xyz.wochib70.web.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.domain.task.ReceivedTaskExpireTime;
import xyz.wochib70.domain.task.TaskCountLimit;
import xyz.wochib70.infrastructure.task.QTaskAwardEntity;
import xyz.wochib70.infrastructure.task.QTaskEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "全局任务查询接口", description = "提供全局任务信息的查询接口")
public class QueryGlobalTasksController {

    private final JPAQueryFactory queryFactory;


    @GetMapping("/query/global-tasks")
    @Operation(summary = "查询全局任务列表", description = "获取所有全局任务的信息")
    @ApiResponse(responseCode = "200", description = "成功返回全局任务列表", 
        content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = QueryGlobalTasksResponse.class)))
    public List<QueryGlobalTasksResponse> queryGlobalTasks() {
        QTaskEntity task = QTaskEntity.taskEntity;
        QTaskAwardEntity award = QTaskAwardEntity.taskAwardEntity;
        Map<Long, Tuple> map = queryFactory.select(
                        task.id,
                        task.name,
                        task.description,
                        task.countLimit,
                        task.countLimitType,
                        task.startTime,
                        task.expiredTime,
                        task.completeEvent,
                        task.receivedTaskExpireTimeType,
                        task.seconds,
                        task.status
                )
                .from(task)
                .where(task.activityId.isNull())
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        it -> it.get(task.id),
                        Function.identity()
                ));

        if (map.isEmpty()) {
            return Collections.emptyList();
        }


        Set<Long> taskIds = map.keySet();

        return queryFactory.select(
                        award.id,
                        award.taskId,
                        award.awardType,
                        award.awardCount,
                        award.awardId
                )
                .from(award)
                .where(award.taskId.in(taskIds))
                .fetch()
                .stream()
                .map(it -> {
                    QueryGlobalTasksResponse response = new QueryGlobalTasksResponse();
                    Tuple tuple = map.get(it.get(award.taskId));
                    response.setId(tuple.get(task.id));
                    response.setName(tuple.get(task.name));
                    response.setDescription(tuple.get(task.description));
                    response.setCountLimit(new TaskCountLimit(
                            tuple.get(task.countLimitType),
                            tuple.get(task.countLimit)
                    ));
                    response.setCompleteEvent(tuple.get(task.completeEvent));
                    response.setReceivedTaskExpireTime(new ReceivedTaskExpireTime(
                            tuple.get(task.receivedTaskExpireTimeType),
                            tuple.get(task.seconds)
                    ));
                    response.setStartTime(tuple.get(task.startTime));
                    response.setExpireTime(tuple.get(task.expiredTime));
                    response.setAward(new QueryGlobalTasksResponse.TaskAward(
                            it.get(award.awardType),
                            it.get(award.awardId),
                            it.get(award.awardCount)
                    ));
                    response.setStatus(tuple.get(task.status));
                    return response;
                })
                .toList();
    }
}