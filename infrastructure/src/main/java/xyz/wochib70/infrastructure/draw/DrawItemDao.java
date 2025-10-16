package xyz.wochib70.infrastructure.draw;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawItemDao extends JpaRepository<DrawItemEntity, Long> {
    
    @Query("SELECT d FROM DrawItemEntity d WHERE d.drawPoolId = :drawPoolId")
    List<DrawItemEntity> findByDrawPoolId(@Param("drawPoolId") Long drawPoolId);
}