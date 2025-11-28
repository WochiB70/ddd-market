package xyz.wochib70.infrastructure.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "outbox")
public class OutBoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String destination;

    private String tag;

    private String content;

    @Enumerated(EnumType.STRING)
    private OutBoxType type;

    private Integer retryCount;

    private Integer maxRetryCount;

    private LocalDateTime createdTime;

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        retryCount = 0;
    }
}
