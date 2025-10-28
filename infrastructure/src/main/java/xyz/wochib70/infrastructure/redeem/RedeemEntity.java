package xyz.wochib70.infrastructure.redeem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.redeem.RedeemParticipateScope;

@Getter
@Setter
@Entity
@Table(name = "redeem")
public class RedeemEntity {

    @Id
    private Long id;

    private Long activityId;

    private String name;

    @Enumerated(EnumType.STRING)
    private RedeemParticipateScope scope;
}
