package xyz.wochib70.infrastructure.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.task.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    private Long id;

    private Long activityId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskCountLimitType countLimitType;

    private int countLimit;

    private LocalDateTime startTime;

    private LocalDateTime expiredTime;

    @Enumerated(EnumType.STRING)
    private CompleteEvent completeEvent;

    @Enumerated(EnumType.STRING)
    private ReceivedTaskExpireTimeType receivedTaskExpireTimeType;

    private Long seconds;

}
