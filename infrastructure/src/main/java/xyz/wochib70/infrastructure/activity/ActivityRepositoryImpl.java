package xyz.wochib70.infrastructure.activity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.activity.*;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ActivityRepositoryImpl implements ActivityRepository {


    private final ActivityDao activityDao;
    private final UserParticipateActivityRecordDao userParticipateActivityRecordDao;

    @Override
    public Integer countParticipateActivityByUserIdInDay(IdentifierId<Long> activityId, UserId userId) {
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInDay(
                activityId.getId(), userId.getId(), LocalDateTime.now());
    }

    @Override
    public Integer countParticipateActivityByUserIdInThisWeek(IdentifierId<Long> activityId, UserId userId) {
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInThisWeek(
                activityId.getId(), userId.getId(), LocalDateTime.now());
    }

    @Override
    public Integer countParticipateActivityByUserIdInRecentlyWeek(IdentifierId<Long> activityId, UserId userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusWeeks(1);
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInRecentlyWeek(
                activityId.getId(), userId.getId(), weekAgo, now);
    }

    @Override
    public Integer countParticipateActivityByUserIdInThisMonth(IdentifierId<Long> activityId, UserId userId) {
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInThisMonth(
                activityId.getId(), userId.getId(), LocalDateTime.now());
    }

    @Override
    public Integer countParticipateActivityByUserIdInRecentlyMonth(IdentifierId<Long> activityId, UserId userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthAgo = now.minusMonths(1);
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInRecentlyMonth(
                activityId.getId(), userId.getId(), monthAgo, now);
    }

    @Override
    public Integer countParticipateActivityByUserIdInYear(IdentifierId<Long> activityId, UserId userId) {
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInYear(
                activityId.getId(), userId.getId(), LocalDateTime.now());
    }

    @Override
    public Integer countParticipateActivityByUserIdInAllTime(IdentifierId<Long> activityId, UserId userId) {
        return userParticipateActivityRecordDao.countParticipateActivityByUserIdInAllTime(
                activityId.getId(), userId.getId());
    }

    @Override
    public void save(Activity activity) {
        ActivityEntity entity = toEntity(activity);
        activityDao.save(entity);
    }

    @Override
    public Activity queryActivityByIdOrThrow(IdentifierId<Long> activityId) {
        return null;
    }

    @Override
    public void update(Activity activity) {
        ActivityEntity entity = toEntity(activity);
        activityDao.updateById(entity);
    }

    @Override
    public Optional<Activity> queryActivityById(IdentifierId<Long> activityId) {
        return activityDao.queryActivityEntitiesById(activityId.getId()).map(this::toDomain);
    }

    private ActivityEntity toEntity(Activity activity) {
        ActivityImpl impl = (ActivityImpl) activity;
        ActivityEntity entity = new ActivityEntity();
        entity.setId(impl.getActivityId().getId());
        entity.setName(impl.getInfo().name());
        entity.setDescription(impl.getInfo().description());
        entity.setImages(impl.getInfo().images());
        entity.setStartTime(impl.getDuration().startTime());
        entity.setEndTime(impl.getDuration().endTime());
        entity.setCountLimitType(impl.getCountLimit().type());
        entity.setCountLimit(impl.getCountLimit().count());
        entity.setCredentialLimit(impl.useCredentialLimit());
        entity.setStatus(impl.getStatus());
        entity.setAwardType(impl.getAwardType());
        return entity;
    }

    private Activity toDomain(ActivityEntity entity) {
        ActivityImpl activity = new ActivityImpl(new DefaultIdentifierId<>(entity.getId()));
        activity.setInfo(new ActivityInfo(entity.getName(), entity.getDescription(), entity.getImages()));
        activity.setDuration(new ActivityDuration(entity.getStartTime(), entity.getEndTime()));
        activity.setCountLimit(new ActivityCountLimit(entity.getCountLimitType(), entity.getCountLimit()));
        activity.setCredentialLimit(entity.getCredentialLimit());
        activity.setStatus(entity.getStatus());
        activity.setAwardType(entity.getAwardType());
        return activity;
    }
}
