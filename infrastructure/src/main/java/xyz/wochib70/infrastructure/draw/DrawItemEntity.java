package xyz.wochib70.infrastructure.draw;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.draw.DrawItemType;

@Getter
@Setter
@Entity
@Table(name = "draw_item")
public class DrawItemEntity {
    
    @Id
    private Long id;
    
    private Long drawPoolId;
    
    private String name;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private DrawItemType type;
    
    private Integer weight;

}