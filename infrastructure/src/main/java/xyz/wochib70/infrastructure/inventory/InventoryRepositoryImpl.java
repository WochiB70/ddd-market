package xyz.wochib70.infrastructure.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.inventory.InsufficientInventoryException;
import xyz.wochib70.domain.inventory.InventoryRepository;
import xyz.wochib70.domain.inventory.Inventory;
import xyz.wochib70.domain.inventory.InventoryImpl;
import xyz.wochib70.domain.inventory.GoodsType;
import xyz.wochib70.domain.inventory.NoSuchInventoryException;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryDao inventoryDao;
    private final InventoryRecordDao inventoryRecordDao;

    @Override
    public void save(Inventory inventory) {
        InventoryEntity entity = new InventoryEntity();
        entity.setId(inventory.getInventoryId().getId());
        entity.setGoodsId(((InventoryImpl) inventory).getGoodsId().getId());
        entity.setType(((InventoryImpl) inventory).getGoodsType());
        entity.setCount(((InventoryImpl) inventory).getCount());
        entity.setInventoryType(((InventoryImpl) inventory).getType());
        inventoryDao.save(entity);
    }

    @Override
    public void update(Inventory inventory) {
        save(inventory);
    }

    @Override
    public Inventory queryByGoodsIdAndGoodsTypeOrThrow(IdentifierId<Long> goodsId, GoodsType goodsType) throws NoSuchInventoryException {
        Optional<InventoryEntity> entityOptional = inventoryDao.findByGoodsIdAndType(goodsId.getId(), goodsType);
        if (entityOptional.isPresent()) {
            InventoryEntity entity = entityOptional.get();
            InventoryImpl inventory = new InventoryImpl(new DefaultIdentifierId<>(entity.getId()));
            inventory.setGoodsId(new DefaultIdentifierId<>(entity.getGoodsId()));
            inventory.setGoodsType(entity.getType());
            inventory.setType(entity.getInventoryType());
            inventory.setCount(entity.getCount());
            return inventory;
        } else {
            throw new NoSuchInventoryException("库存不存在");
        }
    }

    @Override
    public void generateInventoryRecord(IdentifierId<Long> inventoryId, Integer count) {
        List<InventoryRecordEntity> records = IntStream.range(0, count)
                .mapToObj(i -> {
                    InventoryRecordEntity record = new InventoryRecordEntity();
                    record.setInventoryId(inventoryId.getId());
                    record.setStatus(InventoryRecordStatus.CREATED);
                    record.setVersion(0L);
                    return record;
                })
                .toList();
        inventoryRecordDao.saveAll(records);
    }

    @Override
    public void removeInventoryRecord(IdentifierId<Long> inventoryId, Integer count) throws InsufficientInventoryException {
        List<InventoryRecordEntity> records = inventoryRecordDao.findByInventoryIdAndStatus(inventoryId.getId(), InventoryRecordStatus.CREATED);
        if (records.size() < count) {
            throw new InsufficientInventoryException("奖品库存不足");
        }

        List<InventoryRecordEntity> recordsToRemove = records.subList(0, count);
        inventoryRecordDao.deleteAll(recordsToRemove);
    }

    @Override
    public void useInventoryRecord(IdentifierId<Long> inventoryId, Integer count) throws InsufficientInventoryException {
        List<InventoryRecordEntity> records = inventoryRecordDao.findByInventoryIdAndStatus(inventoryId.getId(), InventoryRecordStatus.CREATED);
        if (records.size() < count) {
            throw new InsufficientInventoryException("奖品库存不足");
        }

        records.subList(0, count).forEach(record -> record.setStatus(InventoryRecordStatus.USED));
        inventoryRecordDao.saveAll(records.subList(0, count));
    }

    @Override
    public void removeUnusedInventoryRecord(IdentifierId<Long> inventoryId) {
        List<InventoryRecordEntity> unusedRecords = inventoryRecordDao.findByInventoryIdAndStatus(inventoryId.getId(), InventoryRecordStatus.CREATED);
        unusedRecords.forEach(record -> record.setStatus(InventoryRecordStatus.DELETED));
        inventoryRecordDao.saveAllAndFlush(unusedRecords);
    }
}