package xyz.wochib70.infrastructure.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.wochib70.domain.task.CompleteEvent;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDao extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t WHERE t.id = :id")
    Optional<TaskEntity> queryTaskById(Long id);
    
    @Query("SELECT t FROM TaskEntity t WHERE t.activityId = :activityId")
    List<TaskEntity> findByActivityId(@Param("activityId") Long activityId);
    
    List<TaskEntity> findByCompleteEvent(CompleteEvent completeEvent);
}