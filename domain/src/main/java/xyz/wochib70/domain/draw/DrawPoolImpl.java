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
        drawItems.stream().filter(award -> Objects.equals(award.getId(), awardId))
                .findFirst()
                .ifPresentOrElse(award -> {
                            if (!Objects.equals(award.getDrawItemInventory(), inventory)) {
                                award.setDrawItemInventory(inventory);
                                publishEvent(new DrawItemInventoryModifiedEvent(
                                        getDrawPoolId(),
                                        awardId,
                                        inventory
                                ));
                            }
                        },
                        () -> {
                            throw new NoSuchAwardException("Award not found, id = " + awardId);
                        });
    }

    @Override
    public void modifyDrawItemBasicInfo(IdentifierId<Long> awardId, String name, String description) {
        if (name == null) {
            throw new IllegalArgumentException("名称不能为null");
        }
        drawItems.stream()
                .filter(award -> !Objects.equals(award.getId(), awardId))
                .filter(award -> Objects.equals(award.getName(), name))
                .findFirst()
                .ifPresent(award -> {
                    throw new DuplicateAwardException("修改的name已存在, name = " + name);
                });

        drawItems.stream().filter(award -> Objects.equals(award.getId(), awardId))
                .findFirst()
                .ifPresentOrElse(award -> {
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
                        },
                        () -> {
                            throw new NoSuchAwardException("Award not found, id = " + awardId);
                        });
    }

    @Override
    public void modifyDrawItemType(IdentifierId<Long> awardId, DrawItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("AwardType不能为null");
        }
        drawItems.stream().filter(award -> Objects.equals(award.getId(), awardId))
                .findFirst()
                .ifPresentOrElse(award -> {
                            if (!Objects.equals(award.getType(), type)) {
                                award.setType(type);
                                publishEvent(new DrawItemTypeModifiedEvent(
                                        getDrawPoolId(),
                                        awardId,
                                        type
                                ));
                            }
                        },
                        () -> {
                            throw new NoSuchAwardException("Award not found, id = " + awardId);
                        });
    }

    @Override
    public void modifyDrawItemWeight(IdentifierId<Long> awardId, Integer weight) {
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("权重不能小于0或为null");
        }
        drawItems.stream().filter(award -> Objects.equals(award.getId(), awardId))
                .findFirst()
                .ifPresentOrElse(award -> {
                            if (!Objects.equals(award.getWeight(), weight)) {
                                award.setWeight(weight);
                                publishEvent(new DrawItemWeightModifiedEvent(
                                        getDrawPoolId(),
                                        awardId,
                                        weight
                                ));
                            }
                        },
                        () -> {
                            throw new NoSuchAwardException("Award not found, id = " + awardId);
                        });
    }

    @Override
    public void addDrawItem(DrawItemInfo drawItemInfo) {
        drawItems.stream().filter(award -> Objects.equals(award.getName(), drawItemInfo.name()))
                .findFirst()
                .ifPresent(award -> {
                    throw new DuplicateAwardException("Award已存在, name = " + drawItemInfo.name());
                });

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
}
