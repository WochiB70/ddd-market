package xyz.wochib70.infrastructure.redeem;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "redeem")
public class RedeemEntity {

    @Id
    private Long id;

    private Long activityId;

    private String name;
}
