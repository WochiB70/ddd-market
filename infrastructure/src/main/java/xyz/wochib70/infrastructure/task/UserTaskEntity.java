package xyz.wochib70.infrastructure.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.usertask.UserTaskStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_task")
public class UserTaskEntity {

    @Id
    private Long id;

    private Long taskId;

    private Long userId;

    private LocalDateTime receivedTime;

    private LocalDateTime expireTime;

    @Enumerated(EnumType.STRING)
    private UserTaskStatus status;
    
    @PrePersist
    protected void onCreate() {
        receivedTime = LocalDateTime.now();
    }
}