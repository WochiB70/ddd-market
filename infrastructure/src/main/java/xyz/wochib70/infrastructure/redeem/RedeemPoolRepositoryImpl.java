package xyz.wochib70.infrastructure.redeem;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.redeem.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RedeemPoolRepositoryImpl implements RedeemRepository {
    
    private final RedeemDao redeemDao;
    private final RedeemItemDao redeemItemDao;

    @Override
    public Redeem findByIdOrThrow(IdentifierId<Long> redeemId) {
        RedeemEntity redeemEntity = redeemDao.findById(redeemId.getId())
                .orElseThrow(() -> new NoSuchRedeemExistsException("兑换池不存在"));
        
        List<RedeemItemEntity> itemEntities = redeemItemDao.findByRedeemId(redeemId.getId());
        Set<RedeemItem> redeemItems = itemEntities.stream()
                .map(this::toRedeemItemDomain)
                .collect(Collectors.toSet());
        
        return toDomain(redeemEntity, redeemItems);
    }

    @Override
    public void save(Redeem redeem) {
        RedeemEntity redeemEntity = toEntity(redeem);
        RedeemEntity savedEntity = redeemDao.save(redeemEntity);
        
        // 保存兑换项
        RedeemImpl impl = (RedeemImpl) redeem;
        if (impl.getRedeemItems() != null) {
            impl.getRedeemItems().forEach(item -> {
                RedeemItemEntity itemEntity = toRedeemItemEntity(item);
                itemEntity.setRedeemId(savedEntity.getId());
                redeemItemDao.save(itemEntity);
            });
        }
    }

    @Override
    public void delete(Redeem redeem) {
        RedeemEntity entity = toEntity(redeem);
        redeemDao.delete(entity);
        
        // 删除关联的兑换项
        redeemItemDao.deleteAll(redeemItemDao.findByRedeemId(redeem.getRedeemId().getId()));
    }

    @Override
    public void update(Redeem redeem) {
        RedeemEntity redeemEntity = toEntity(redeem);
        redeemDao.save(redeemEntity);
        
        // 先删除现有的兑换项，再重新保存
        redeemItemDao.deleteAll(redeemItemDao.findByRedeemId(redeem.getRedeemId().getId()));
        
        // 保存兑换项
        RedeemImpl impl = (RedeemImpl) redeem;
        if (impl.getRedeemItems() != null) {
            impl.getRedeemItems().forEach(item -> {
                RedeemItemEntity itemEntity = toRedeemItemEntity(item);
                itemEntity.setRedeemId(redeemEntity.getId());
                redeemItemDao.save(itemEntity);
            });
        }
    }

    @Override
    public List<Redeem> findByActivityId(IdentifierId<Long> activityId) {
        List<RedeemEntity> redeemEntities = redeemDao.findByActivityId(activityId.getId());
        
        return redeemEntities.stream()
                .map(entity -> {
                    List<RedeemItemEntity> itemEntities = redeemItemDao.findByRedeemId(entity.getId());
                    Set<RedeemItem> redeemItems = itemEntities.stream()
                            .map(this::toRedeemItemDomain)
                            .collect(Collectors.toSet());
                    return toDomain(entity, redeemItems);
                })
                .collect(Collectors.toList());
    }
    
    private Redeem toDomain(RedeemEntity entity, Set<RedeemItem> redeemItems) {
        RedeemImpl redeem = new RedeemImpl(new DefaultIdentifierId<>(entity.getId()));
        redeem.setActivityId(new DefaultIdentifierId<>(entity.getActivityId()));
        redeem.setName(entity.getName());
        redeem.setRedeemItems(redeemItems);
        return redeem;
    }
    
    private RedeemEntity toEntity(Redeem redeem) {
        RedeemImpl impl = (RedeemImpl) redeem;
        RedeemEntity entity = new RedeemEntity();
        entity.setId(impl.getRedeemId().getId());
        entity.setActivityId(impl.getActivityId().getId());
        entity.setName(impl.getName());
        return entity;
    }
    
    private RedeemItem toRedeemItemDomain(RedeemItemEntity entity) {
        RedeemItemInventory inventory = new RedeemItemInventory(entity.getInventoryType(), entity.getValidCount());
        RedeemItemPrice price = new RedeemItemPrice(new DefaultIdentifierId<>(entity.getCurrencyId()), entity.getPrice());

        RedeemItem redeemItem = new RedeemItem();
        redeemItem.setId(new DefaultIdentifierId<>(entity.getId()));
        redeemItem.setItemInfo(entity.getName(), entity.getDescription());
        redeemItem.setItemType(entity.getType());
        redeemItem.setItemPrice(price);
        redeemItem.setInventory(inventory);
        return redeemItem;
    }
    
    private RedeemItemEntity toRedeemItemEntity(RedeemItem redeemItem) {
        RedeemItemEntity entity = new RedeemItemEntity();
        entity.setId(redeemItem.getId().getId());
        entity.setName(redeemItem.getName());
        entity.setDescription(redeemItem.getDescription());
        entity.setInventoryType(redeemItem.getInventory().getType());
        entity.setValidCount(redeemItem.getInventory().getValidCount());
        entity.setCurrencyId(redeemItem.getPrice().currencyId().getId());
        entity.setPrice(redeemItem.getPrice().price());
        entity.setType(redeemItem.getType());
        return entity;
    }
}