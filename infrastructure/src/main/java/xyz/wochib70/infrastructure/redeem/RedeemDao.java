package xyz.wochib70.infrastructure.redeem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedeemDao extends JpaRepository<RedeemEntity, Long> {
    
    @Query("SELECT r FROM RedeemEntity r WHERE r.activityId = :activityId")
    List<RedeemEntity> findByActivityId(@Param("activityId") Long activityId);
}