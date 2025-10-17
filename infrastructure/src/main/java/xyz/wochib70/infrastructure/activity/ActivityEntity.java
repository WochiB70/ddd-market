package xyz.wochib70.infrastructure.activity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.activity.ActivityAwardType;
import xyz.wochib70.domain.activity.ActivityStatus;
import xyz.wochib70.domain.activity.CountLimitType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "activity")
public class ActivityEntity {
    @Id
    private Long id;

    private String name;

    private String description;

    private List<String> images;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private CountLimitType countLimitType;

    private Integer countLimit;

    private Boolean credentialLimit;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @Enumerated(EnumType.STRING)
    private ActivityAwardType awardType;
}
