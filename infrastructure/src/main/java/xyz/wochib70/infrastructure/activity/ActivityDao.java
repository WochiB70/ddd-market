package xyz.wochib70.infrastructure.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.wochib70.domain.activity.Activity;

import java.util.Optional;

@Repository
public interface ActivityDao extends JpaRepository<ActivityEntity, Long> {

    void updateById(ActivityEntity entity);

    Optional<ActivityEntity> queryActivityEntitiesById(Long id);
}
