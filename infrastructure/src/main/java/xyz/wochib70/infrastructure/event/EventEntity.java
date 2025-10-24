package xyz.wochib70.infrastructure.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    private Long id;

    private String aggregateClass;

    private LocalDateTime createdTime;

    private String eventClass;

    @Column(columnDefinition = "TEXT")
    private String content;
}
