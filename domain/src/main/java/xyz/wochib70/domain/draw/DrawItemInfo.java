package xyz.wochib70.domain.draw;

public record DrawItemInfo(
        String name,
        String description,
        DrawItemType type,
        Integer weight,
        DrawItemInventory inventory
) {

    public DrawItemInfo {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("DrawItem的name不能为null或者是空白字符串");
        }

        if (type == null) {
            throw new IllegalArgumentException("DrawItem的type不能null");
        }

        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("DrawItem的weight不能为null 或者 小于等于0");
        }

        if (inventory == null) {
            throw new IllegalArgumentException("DrawItem inventory不能为null");
        }
    }
}
