package xyz.wochib70.infrastructure.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityImageDao extends JpaRepository<ActivityImageEntity, Long> {
    List<ActivityImageEntity> findByActivityId(Long activityId);

    void deleteByActivityId(Long activityId);
}
