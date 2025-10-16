package xyz.wochib70.infrastructure.activity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_participate_activity_record")
public class UserParticipateActivityRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long activityId;

    private Long userId;

    private LocalDateTime participateTime;
}