package xyz.wochib70.infrastructure.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityDao extends JpaRepository<ActivityEntity, Long> {


    Optional<ActivityEntity> queryActivityEntitiesById(Long id);
}
