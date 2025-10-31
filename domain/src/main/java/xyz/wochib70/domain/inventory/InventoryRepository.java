package xyz.wochib70.domain.inventory;

import xyz.wochib70.domain.IdentifierId;

public interface InventoryRepository {


    /**
     * 保存库存
     *
     * @param inventory 库存
     */
    void save(Inventory inventory);


    /**
     * 更新库存
     *
     * @param inventory 库存
     */
    void update(Inventory inventory);


    /**
     * 查询库存
     *
     * @param goodsId   商品Id
     * @param goodsType 商品类型
     * @return 库存
     * @throws NoSuchInventoryException 库存不存在
     */
    Inventory queryByGoodsIdAndGoodsTypeOrThrow(IdentifierId<Long> goodsId, GoodsType goodsType) throws NoSuchInventoryException;


    /**
     * 生成库存记录
     *
     * @param inventoryId 库存Id
     * @param count       库存数量
     */
    void generateInventoryRecord(IdentifierId<Long> inventoryId, Integer count);

    /**
     * 移除库存记录
     *
     * @param inventoryId 库存Id
     * @param count       库存数量
     * @throws InsufficientInventoryException 奖品库存不足
     *
     */
    void removeInventoryRecord(IdentifierId<Long> inventoryId, Integer count) throws InsufficientInventoryException;

    /**
     * 使用库存记录
     *
     * @param inventoryId 库存Id
     * @param count       库存数量
     * @throws InsufficientInventoryException 奖品库存不足
     */
    void useInventoryRecord(IdentifierId<Long> inventoryId, Integer count) throws InsufficientInventoryException;

    /**
     * 移除未使用的库存记录
     *
     * @param inventoryId 库存Id
     */
    void removeUnusedInventoryRecord(IdentifierId<Long> inventoryId);
}
