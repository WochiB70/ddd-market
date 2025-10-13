package xyz.wochib70.domain.draw;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.events.*;
import xyz.wochib70.domain.draw.strategy.RandomDrawStrategy;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public non-sealed class DrawPoolImpl extends AbstractAggregate<Long> implements DrawPool {

    private Set<DrawItem> drawItems;

    private String name;

    private IdentifierId<Long> activityId;

    private DrawStrategyType strategyType;

    private DrawPrice drawPrice;


    public DrawPoolImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getDrawPoolId() {
        return identifierId();
    }

    @Override
    public Reward draw(UserId userId) {
        ParameterUtil.requireNonNull(userId, "用户id不能为null");
        IdentifierId<Long> awardId = switch (strategyType) {
            case RANDOM -> new RandomDrawStrategy().draw(drawItems, userId);
        };
        publishEvent(new AwardReceivedEvent(
                getDrawPoolId(),
                getActivityId(),
                awardId,
                userId,
                1
        ));
        return new Reward(awardId, 1);
    }

    @Override
    public void modifyDrawPoolName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("名称不能为null或空");
        }
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            publishEvent(new DrawPoolNameModifiedEvent(
                    getDrawPoolId(),
                    name
            ));
        }
    }

    @Override
    public void modifyDrawStrategy(DrawStrategyType drawStrategyType) {
        ParameterUtil.requireNonNull(drawStrategyType, "抽奖策略不能为null");
        if (!Objects.equals(strategyType, drawStrategyType)) {
            this.strategyType = drawStrategyType;
            publishEvent(new DrawStrategyModifiedEvent(
                    getDrawPoolId(),
                    strategyType
            ));
        }
    }

    @Override
    public void modifyDrawPrice(DrawPrice drawPrice) {
        ParameterUtil.requireNonNull(drawPrice, "抽奖价格不能为null");
        if (!Objects.equals(this.drawPrice, drawPrice)){
            this.drawPrice = drawPrice;
            publishEvent(new DrawPoolPriceModifiedEvent(
                    getDrawPoolId(),
                    drawPrice
            ));
        }
    }

    @Override
    public void modifyDrawItemInventory(IdentifierId<Long> awardId, DrawItemInventory inventory) {
        DrawItem award = findDrawItemOrThrow(awardId);
        if (!Objects.equals(award.getDrawItemInventory(), inventory)) {
            award.setDrawItemInventory(inventory);
            publishEvent(new DrawItemInventoryModifiedEvent(
                    getDrawPoolId(),
                    awardId,
                    inventory
            ));
        }
    }

    @Override
    public void modifyDrawItemBasicInfo(IdentifierId<Long> awardId, String name, String description) {
        if (name == null) {
            throw new IllegalArgumentException("名称不能为null");
        }
        checkDuplicateDrawItemName(name, awardId);

        DrawItem award = findDrawItemOrThrow(awardId);
        if (!Objects.equals(award.getName(), name) || !Objects.equals(award.getDescription(), description)) {
            award.setName(name);
            award.setDescription(description);
            publishEvent(new DrawItemBasicInfoModifiedEvent(
                    getDrawPoolId(),
                    awardId,
                    name,
                    description
            ));
        }
    }

    @Override
    public void modifyDrawItemType(IdentifierId<Long> awardId, DrawItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("AwardType不能为null");
        }
        DrawItem award = findDrawItemOrThrow(awardId);
        if (!Objects.equals(award.getType(), type)) {
            award.setType(type);
            publishEvent(new DrawItemTypeModifiedEvent(
                    getDrawPoolId(),
                    awardId,
                    type
            ));
        }
    }

    @Override
    public void modifyDrawItemWeight(IdentifierId<Long> awardId, Integer weight) {
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("权重不能小于0或为null");
        }
        DrawItem award = findDrawItemOrThrow(awardId);
        if (!Objects.equals(award.getWeight(), weight)) {
            award.setWeight(weight);
            publishEvent(new DrawItemWeightModifiedEvent(
                    getDrawPoolId(),
                    awardId,
                    weight
            ));
        }
    }

    @Override
    public void addDrawItem(DrawItemInfo drawItemInfo) {
        checkDuplicateDrawItemName(drawItemInfo.name(), null);

        DrawItem drawItem = new DrawItem(
                DrawDomainRegistry.awardIdGenerator().nextAwardId(),
                drawItemInfo.name(),
                drawItemInfo.description(),
                drawItemInfo.type(),
                drawItemInfo.weight(),
                drawItemInfo.inventory()
        );
        drawItems.add(drawItem);
        publishEvent(new DrawItemAddedEvent(
                getDrawPoolId(),
                drawItem
        ));
    }

    @Override
    public void removeDrawItem(IdentifierId<Long> awardId) {
        if (drawItems.removeIf(award -> Objects.equals(award.getId(), awardId))) {
            publishEvent(new DrawItemRemovedEvent(
                    getDrawPoolId(),
                    awardId
            ));
        }

    }

    @Override
    public void create() {
        publishEvent(new DrawPoolCreatedEvent(
                this
        ));
    }

    @Override
    public void delete() {
        publishEvent(new DrawPoolDeletedEvent(
                this
        ));
    }

    /**
     * 根据ID查找DrawItem，如果不存在则抛出异常
     *
     * @param awardId 奖品ID
     * @return DrawItem
     * @throws NoSuchAwardException 如果奖品不存在
     */
    private DrawItem findDrawItemOrThrow(IdentifierId<Long> awardId) {
        return drawItems.stream()
                .filter(award -> Objects.equals(award.getId(), awardId))
                .findFirst()
                .orElseThrow(() -> new NoSuchAwardException("Award not found, id = " + awardId));
    }

    /**
     * 检查是否存在重复的奖品名称（排除指定ID的奖品）
     *
     * @param name      要检查的名称
     * @param excludeId 要排除的奖品ID（可以为null）
     * @throws DuplicateAwardException 如果存在重复名称
     */
    private void checkDuplicateDrawItemName(String name, IdentifierId<Long> excludeId) {
        drawItems.stream()
                .filter(award -> excludeId == null || !Objects.equals(award.getId(), excludeId))
                .filter(award -> Objects.equals(award.getName(), name))
                .findFirst()
                .ifPresent(award -> {
                    throw new DuplicateAwardException("修改的name已存在, name = " + name);
                });
    }
}
