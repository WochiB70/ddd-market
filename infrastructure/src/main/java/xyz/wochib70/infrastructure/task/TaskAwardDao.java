package xyz.wochib70.infrastructure.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAwardDao extends JpaRepository<TaskAwardEntity, Long> {

    @Query("SELECT t FROM TaskAwardEntity t WHERE t.taskId = :taskId")
    TaskAwardEntity queryByTaskId(@Param("taskId") Long taskId);

    void deleteByTaskId(Long taskId);
}
