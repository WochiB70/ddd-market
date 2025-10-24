package xyz.wochib70.web.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wochib70.infrastructure.activity.ActivityImageEntity;
import xyz.wochib70.infrastructure.activity.QActivityEntity;
import xyz.wochib70.infrastructure.activity.QActivityImageEntity;
import xyz.wochib70.infrastructure.activity.QUserParticipateActivityRecordEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@Tag(name = "活动卡片查询接口", description = "提供活动卡片相关信息的查询接口")
public class QueryActivityCardsController {

    private final JPAQueryFactory queryFactory;

    @GetMapping("/query/activity-cards")
    @Operation(summary = "查询活动卡片列表", description = "获取所有活动的基本信息和参与人数统计")
    @ApiResponse(responseCode = "200", description = "成功返回活动卡片列表",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = QueryActivityCardsResponse.class)))
    public List<QueryActivityCardsResponse> queryActivityCards() {
        QActivityEntity activity = QActivityEntity.activityEntity;
        QUserParticipateActivityRecordEntity record = QUserParticipateActivityRecordEntity.userParticipateActivityRecordEntity;
        QActivityImageEntity image = QActivityImageEntity.activityImageEntity;
        Map<Long, Long> map = queryFactory.select(record.activityId, record.activityId.count())
                .from(record)
                .groupBy(record.activityId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        it -> it.get(record.id),
                        it -> {
                            Long l = it.get(record.id.count());
                            return Objects.isNull(l) ? 0 : l;
                        }
                ));

        return queryFactory.select(
                        activity.id,
                        activity.name,
                        activity.description,
                        activity.status,
                        activity.awardType,
                        activity.startTime,
                        activity.endTime,
                        activity.status
                )
                .from(activity)
                .fetch()
                .stream()
                .map(it -> {
                    QueryActivityCardsResponse response = new QueryActivityCardsResponse();
                    response.setId(it.get(activity.id));
                    response.setName(it.get(activity.name));
                    ActivityImageEntity entity = queryFactory.select(image)
                            .from(image)
                            .where(image.activityId.eq(it.get(activity.id)))
                            .fetchFirst();
                    response.setImage(Objects.isNull(entity) ? null : entity.getImage());
                    response.setDescription(it.get(activity.description));
                    response.setStatus(it.get(activity.status));
                    response.setAwardType(it.get(activity.awardType));
                    response.setDurationDetail(new QueryActivityCardsResponse.Duration(
                            it.get(activity.startTime),
                            it.get(activity.endTime)
                    ));
                    response.setStatus(it.get(activity.status));
                    response.setParticipantCount(map.getOrDefault(it.get(activity.id), 0L));
                    return response;
                })
                .toList();
    }
}