package xyz.wochib70.infrastructure.draw;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawPoolDao extends JpaRepository<DrawPoolEntity, Long> {
    
    @Query("SELECT d FROM DrawPoolEntity d WHERE d.activityId = :activityId")
    List<DrawPoolEntity> findByActivityId(@Param("activityId") Long activityId);
}