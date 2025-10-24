package xyz.wochib70.infrastructure.draw;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.draw.DrawStrategyType;

@Getter
@Setter
@Entity
@Table(name = "draw_pool")
public class DrawPoolEntity {

    @Id
    private Long id;
    
    private String name;
    
    private Long activityId;
    
    @Enumerated(EnumType.STRING)
    private DrawStrategyType strategyType;

    private Long currencyId;

    private Integer price;
}