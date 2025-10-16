package xyz.wochib70.infrastructure.redeem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedeemItemDao extends JpaRepository<RedeemItemEntity, Long> {
    
    @Query("SELECT r FROM RedeemItemEntity r WHERE r.redeemId = :redeemId")
    List<RedeemItemEntity> findByRedeemId(@Param("redeemId") Long redeemId);
}