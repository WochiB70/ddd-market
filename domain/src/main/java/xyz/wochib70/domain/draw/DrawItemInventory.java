package xyz.wochib70.domain.draw;

import java.util.Objects;

public class DrawItemInventory {

    private final DrawInventoryType type;

    private Integer surplus;

    public DrawItemInventory(DrawInventoryType type, Integer surplus) {
        Objects.requireNonNull(type, "奖品库存类型不能为null");
        Objects.requireNonNull(surplus, "奖品库存不能为null");
        if (Objects.equals(type, DrawInventoryType.LIMITED) && surplus < 0) {
            throw new IllegalArgumentException("奖品库存不能小于0");
        }
        this.type = type;
        this.surplus = surplus;
    }

    /**
     * 领取奖品
     *
     * @throws InsufficientInventoryException 奖品库存不足
     */
    public void receive() {
        if (type == DrawInventoryType.INFINITE) {
            return;
        }
        if (surplus <= 0) {
            throw new InsufficientInventoryException("奖品库存不足");
        }
        surplus--;
    }

    public boolean validInventory() {
        if (type == DrawInventoryType.INFINITE) {
            return true;
        }
        return surplus > 0;
    }
}
