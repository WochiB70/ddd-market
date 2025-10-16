package xyz.wochib70.infrastructure.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<EventEntity, Long> {
}
