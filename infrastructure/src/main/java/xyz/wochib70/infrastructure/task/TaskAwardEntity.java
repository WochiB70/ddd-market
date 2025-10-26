package xyz.wochib70.infrastructure.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.task.TaskAwardType;

@Getter
@Setter
@Entity
@Table(name = "task_award")
public class TaskAwardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long awardId;

    private Long taskId;

    private int awardCount;

    @Enumerated(EnumType.STRING)
    private TaskAwardType awardType;
}
