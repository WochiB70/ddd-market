package xyz.wochib70.infrastructure.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.wochib70.domain.usertask.UserTaskStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserTaskDao extends JpaRepository<UserTaskEntity, Long> {
    
    @Query("SELECT u FROM UserTaskEntity u WHERE u.id = :userTaskId AND u.userId = :userId AND u.status != :status")
    Optional<UserTaskEntity> findUncompletedByIdAndUserId(@Param("userTaskId") Long userTaskId, 
                                                         @Param("userId") Long userId, 
                                                         @Param("status") UserTaskStatus status);
    
    @Query("SELECT u FROM UserTaskEntity u WHERE u.userId = :userId AND u.taskId = :taskId AND u.status != :status")
    Optional<UserTaskEntity> findUncompletedByUserIdAndTaskId(@Param("userId") Long userId, 
                                                              @Param("taskId") Long taskId, 
                                                              @Param("status") UserTaskStatus status);
    
    Optional<UserTaskEntity> findById(Long id);
    
    @Query("SELECT COUNT(u) FROM UserTaskEntity u WHERE u.userId = :userId AND u.taskId = :taskId AND u.receivedTime BETWEEN :startTime AND :endTime")
    Integer countByUserIdAndTaskIdAndCreateTimeBetween(@Param("userId") Long userId,
                                                       @Param("taskId") Long taskId,
                                                       @Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime);
}