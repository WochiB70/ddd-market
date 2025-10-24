package xyz.wochib70.web.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.infrastructure.activity.ActivityImageEntity;
import xyz.wochib70.infrastructure.activity.QActivityEntity;
import xyz.wochib70.infrastructure.activity.QActivityImageEntity;
import xyz.wochib70.infrastructure.task.QTaskEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "活动详情查询接口", description = "提供活动详细信息的查询接口")
public class QueryActivityDetailController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/activity-detail/{activityId}")
    @Operation(summary = "查询活动详情", description = "根据活动ID获取活动的详细信息")
    @ApiResponse(responseCode = "200", description = "成功返回活动详情",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryActivityDetailResponse.class)))
    public QueryActivityDetailResponse queryActivityDetail(
            @Parameter(description = "活动ID", example = "1")
            @PathVariable("activityId") Long activityId
    ) {
        QActivityEntity activity = QActivityEntity.activityEntity;
        QTaskEntity task = QTaskEntity.taskEntity;
        QActivityImageEntity image = QActivityImageEntity.activityImageEntity;

        Map<Long, List<String>> images = queryFactory.select(
                        image.image,
                        image.activityId
                )
                .from(image)
                .fetch()
                .stream()
                .map(it -> {
                    ActivityImageEntity entity = new ActivityImageEntity();
                    entity.setImage(it.get(image.image));
                    entity.setActivityId(it.get(image.activityId));
                    return entity;
                })
                .collect(Collectors.toMap(
                        ActivityImageEntity::getActivityId,
                        it -> {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(it.getImage());
                            return list;
                        },
                        (old, newList) -> {
                            old.addAll(newList);
                            return old;
                        }
                ));

        Long taskCount = queryFactory.select(task.id.count())
                .from(task)
                .where(task.activityId.eq(activityId))
                .fetchOne();

        return queryFactory
                .select(
                        activity.id,
                        activity.name,
                        activity.description,
                        activity.startTime,
                        activity.endTime,
                        activity.countLimitType,
                        activity.countLimit,
                        activity.credentialLimit,
                        activity.awardType
                )
                .from(activity)
                .where(activity.id.eq(activityId))
                .fetch()
                .stream()
                .map(it ->
                        new QueryActivityDetailResponse(
                                it.get(activity.id),
                                it.get(activity.name),
                                it.get(activity.description),
                                images.getOrDefault(it.get(activity.id), Collections.emptyList()),
                                it.get(activity.startTime),
                                it.get(activity.endTime),
                                it.get(activity.countLimitType),
                                it.get(activity.countLimit),
                                it.get(activity.credentialLimit),
                                it.get(activity.awardType),
                                taskCount
                        ))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("不存在的活动"));

    }
}