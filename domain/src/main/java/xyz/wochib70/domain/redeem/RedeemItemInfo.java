package xyz.wochib70.domain.redeem;

public record RedeemItemInfo(
        String name,
        String description,
        RedeemItemType type,
        RedeemItemPrice price,
        RedeemItemInventory inventory
) {

    public RedeemItemInfo {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("兑换项的名称不能为null或空");
        }
        if (type == null) {
            throw new IllegalArgumentException("兑换项的类型不能为null");
        }
        if (price == null) {
            throw new IllegalArgumentException("兑换项的价格不能为null");
        }
        if (inventory == null) {
            throw new IllegalArgumentException("兑换项的库存不能为null");
        }
    }
}
