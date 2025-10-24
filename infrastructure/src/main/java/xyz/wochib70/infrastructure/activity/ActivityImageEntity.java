package xyz.wochib70.infrastructure.activity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "activity_image")
public class ActivityImageEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long activityId;

    private String image;
}
