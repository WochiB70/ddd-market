package xyz.wochib70.infrastructure.redeem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.redeem.RedeemItemInventoryType;
import xyz.wochib70.domain.redeem.RedeemItemType;

@Getter
@Setter
@Entity
@Table(name = "redeem_item")
public class RedeemItemEntity {

    @Id
    private Long id;

    private Long redeemId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private RedeemItemInventoryType inventoryType;

    private Integer validCount;

    private Long currencyId;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private RedeemItemType type;
}
