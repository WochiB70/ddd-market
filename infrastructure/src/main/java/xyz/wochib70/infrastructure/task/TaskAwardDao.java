package xyz.wochib70.infrastructure.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAwardDao extends JpaRepository<TaskAwardEntity, Long> {
    TaskAwardEntity queryByTaskId(Long taskId);

    void updateById(TaskAwardEntity awardEntity);
}
