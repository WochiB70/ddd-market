package xyz.wochib70.domain.inventory;

import xyz.wochib70.domain.Aggregate;
import xyz.wochib70.domain.IdentifierId;

public sealed interface Inventory extends Aggregate<Long, Long> permits InventoryImpl {


    /**
     * 获取库存Id
     *
     * @return 库存Id
     */
    IdentifierId<Long> getInventoryId();

    /**
     * 修改库存
     *
     * @param type 库存类型
     */
    void modifyInventory(InventoryType type);


    void useInventory(int count);

    /**
     * 扣减库存
     *
     * @param count   扣减数量
     */
    void decreaseInventory(Integer count);


    /**
     * 增加库存
     *
     * @param count   增加数量
     */
    void increaseInventory(Integer count);
}
