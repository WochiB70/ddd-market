package xyz.wochib70.web.query;

import lombok.Data;
import xyz.wochib70.domain.inventory.InventoryType;
import xyz.wochib70.domain.redeem.RedeemItemType;

@Data
public class QueryRedeemPoolItemResponse {

    private Long id;

    private Long redeemPoolId;

    private String name;

    private String description;

    private RedeemItemType type;

    private RedeemItemInventory inventory;

    private RedeemPrice price;


    public record RedeemItemInventory(
            InventoryType type,
            Integer surplus
    ) {
    }

    public record RedeemPrice(
            Long currencyId,
            Integer price
    ) {
    }
}
