package xyz.wochib70.infrastructure.draw;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.draw.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class DrawPoolRepositoryImpl implements DrawPoolRepository {
    
    private final DrawPoolDao drawPoolDao;
    private final DrawItemDao drawItemDao;

    @Override
    public DrawPool findByIdOrThrow(IdentifierId<Long> drawPoolId) {
        DrawPoolEntity poolEntity = drawPoolDao.findById(drawPoolId.getId())
                .orElseThrow(() -> new NoSuchDrawPoolException("抽奖池不存在"));
        
        List<DrawItemEntity> itemEntities = drawItemDao.findByDrawPoolId(drawPoolId.getId());
        Set<DrawItem> drawItems = itemEntities.stream()
                .map(this::toDrawItemDomain)
                .collect(Collectors.toSet());
        
        return toDomain(poolEntity, drawItems);
    }

    @Override
    public void save(DrawPool drawPool) {
        DrawPoolEntity poolEntity = toEntity(drawPool);
        DrawPoolEntity savedEntity = drawPoolDao.save(poolEntity);
        
        // 保存奖品项
        DrawPoolImpl impl = (DrawPoolImpl) drawPool;
        if (impl.getDrawItems() != null) {
            impl.getDrawItems().forEach(item -> {
                DrawItemEntity itemEntity = toItemEntity(item);
                itemEntity.setDrawPoolId(savedEntity.getId());
                drawItemDao.save(itemEntity);
            });
        }
    }

    @Override
    public void update(DrawPool drawPool) {
        DrawPoolEntity poolEntity = toEntity(drawPool);
        drawPoolDao.save(poolEntity);
        
        // 先删除现有的奖品项，再重新保存
        drawItemDao.deleteAll(drawItemDao.findByDrawPoolId(drawPool.getDrawPoolId().getId()));
        
        // 保存奖品项
        DrawPoolImpl impl = (DrawPoolImpl) drawPool;
        if (impl.getDrawItems() != null) {
            impl.getDrawItems().forEach(item -> {
                DrawItemEntity itemEntity = toItemEntity(item);
                itemEntity.setDrawPoolId(poolEntity.getId());
                drawItemDao.save(itemEntity);
            });
        }
    }

    @Override
    public List<DrawPool> findByActivityId(IdentifierId<Long> activityId) {
        List<DrawPoolEntity> poolEntities = drawPoolDao.findByActivityId(activityId.getId());
        
        return poolEntities.stream()
                .map(entity -> {
                    List<DrawItemEntity> itemEntities = drawItemDao.findByDrawPoolId(entity.getId());
                    Set<DrawItem> drawItems = itemEntities.stream()
                            .map(this::toDrawItemDomain)
                            .collect(Collectors.toSet());
                    return toDomain(entity, drawItems);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(DrawPool drawPool) {
        DrawPoolEntity entity = toEntity(drawPool);
        drawPoolDao.delete(entity);
        
        // 删除关联的奖品项
        drawItemDao.deleteAll(drawItemDao.findByDrawPoolId(drawPool.getDrawPoolId().getId()));
    }
    
    private DrawPool toDomain(DrawPoolEntity entity, Set<DrawItem> drawItems) {
        DrawPoolImpl drawPool = new DrawPoolImpl(new DefaultIdentifierId<>(entity.getId()));
        drawPool.setName(entity.getName());
        drawPool.setActivityId(new DefaultIdentifierId<>(entity.getActivityId()));
        drawPool.setStrategyType(entity.getStrategyType());
        drawPool.setDrawItems(drawItems);
        drawPool.setDrawPrice(new DrawPrice(
                new DefaultIdentifierId<>(entity.getCurrencyId()),
                entity.getPrice()
        ));
        drawPool.setScope(entity.getScope());
        return drawPool;
    }
    
    private DrawPoolEntity toEntity(DrawPool drawPool) {
        DrawPoolImpl impl = (DrawPoolImpl) drawPool;
        DrawPoolEntity entity = new DrawPoolEntity();
        entity.setId(impl.getDrawPoolId().getId());
        entity.setName(impl.getName());
        entity.setActivityId(impl.getActivityId().getId());
        entity.setStrategyType(impl.getStrategyType());
        entity.setCurrencyId(impl.getDrawPrice().currencyId().getId());
        entity.setPrice(impl.getDrawPrice().price());
        entity.setScope(impl.getScope());
        return entity;
    }
    
    private DrawItem toDrawItemDomain(DrawItemEntity entity) {
        return new DrawItem(new DefaultIdentifierId<>(
                entity.getId()),
                entity.getName(),
                entity.getDescription(),
                entity.getType(),
                entity.getWeight()
        );
    }
    
    private DrawItemEntity toItemEntity(DrawItem drawItem) {
        DrawItemEntity entity = new DrawItemEntity();
        entity.setId(drawItem.getId().getId());
        entity.setName(drawItem.getName());
        entity.setDescription(drawItem.getDescription());
        entity.setType(drawItem.getType());
        entity.setWeight(drawItem.getWeight());
        return entity;
    }
}