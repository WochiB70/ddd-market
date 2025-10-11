package xyz.wochib70.domain.draw;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.DomainException;
import xyz.wochib70.domain.IdentifierId;

import java.util.Objects;

@Setter
@Getter
public class DrawItem {

    private final IdentifierId<Long> id;

    private String name;

    private String description;

    private DrawItemType type;

    private Integer weight;

    private DrawItemInventory drawItemInventory;


    public DrawItem(
            IdentifierId<Long> id,
            String name,
            String description,
            DrawItemType type,
            Integer weight,
            DrawItemInventory drawItemInventory
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.weight = weight;
        this.drawItemInventory = drawItemInventory;
    }


    /**
     * @throws DomainException 如果领取失败
     */
    public void receive() {
        drawItemInventory.receive();
    }

    /**
     * @return true 如果还有库存， false 没有库存
     */
    public boolean validateInventory() {
        return drawItemInventory.validInventory();
    }

    public void setWeight(Integer weight) {
        if (Objects.isNull(weight) || weight < 0) {
            throw new IllegalArgumentException("奖品权重不能为null或者小于0");
        }
        this.weight = weight;
    }

    public void setInventory(DrawItemInventory inventory) {
        Objects.requireNonNull(inventory, "奖品数量不能为空");
        this.drawItemInventory = inventory;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrawItem drawItem = (DrawItem) o;
        return Objects.equals(name, drawItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
