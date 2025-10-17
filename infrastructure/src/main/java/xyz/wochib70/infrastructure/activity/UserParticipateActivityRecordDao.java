package xyz.wochib70.infrastructure.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserParticipateActivityRecordDao extends JpaRepository<UserParticipateActivityRecordEntity, Long> {

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND DATE(r.participateTime) = DATE(:participateTime)")
    Integer countParticipateActivityByUserIdInDay(Long activityId, Long userId, LocalDateTime participateTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND YEARWEEK(r.participateTime, 1) = YEARWEEK(:participateTime, 1)")
    Integer countParticipateActivityByUserIdInThisWeek(Long activityId, Long userId, LocalDateTime participateTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND r.participateTime >= :startTime AND r.participateTime <= :endTime")
    Integer countParticipateActivityByUserIdInRecentlyWeek(Long activityId, Long userId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND YEAR(r.participateTime) = YEAR(:participateTime) AND MONTH(r.participateTime) = MONTH(:participateTime)")
    Integer countParticipateActivityByUserIdInThisMonth(Long activityId, Long userId, LocalDateTime participateTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND r.participateTime >= :startTime AND r.participateTime <= :endTime")
    Integer countParticipateActivityByUserIdInRecentlyMonth(Long activityId, Long userId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId AND YEAR(r.participateTime) = YEAR(:participateTime)")
    Integer countParticipateActivityByUserIdInYear(Long activityId, Long userId, LocalDateTime participateTime);

    @Query("SELECT COUNT(r) FROM UserParticipateActivityRecordEntity r WHERE r.activityId = :activityId AND r.userId = :userId")
    Integer countParticipateActivityByUserIdInAllTime(Long activityId, Long userId);
}