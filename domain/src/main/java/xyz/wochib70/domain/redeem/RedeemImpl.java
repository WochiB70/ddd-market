package xyz.wochib70.domain.redeem;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.redeem.events.*;

import java.util.Objects;
import java.util.Set;

import static xyz.wochib70.domain.redeem.RedeemDomainRegistry.redeemItemIdGenerator;

@Getter
@Setter
public non-sealed class RedeemImpl extends AbstractAggregate<Long> implements Redeem {

    private IdentifierId<Long> activityId;

    private String name;

    private Set<RedeemItem> redeemItems;


    public RedeemImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getRedeemId() {
        return identifierId();
    }

    @Override
    public IdentifierId<Long> getActivityId() {
        return activityId;
    }

    @Override
    public void modifyRedeemName(String name) {
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            publishEvent(new RedeemNameModifiedEvent(
                    getRedeemId(),
                    name
            ));
        }
    }

    @Override
    public RedeemItemPrice getRedeemItemPriceOrThrow(IdentifierId<Long> redeemItemId) {
        return redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .map(RedeemItem::getPrice)
                .orElseThrow(() -> new NoSuchRedeemItemException("兑换项不存在"));
    }

    @Override
    public void redeem(IdentifierId<Long> redeemItemId, int count, UserId userId) {
        if (count <= 0) {
            throw new IllegalArgumentException("兑换数量不能小于0");
        }
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .ifPresentOrElse(redeemItem -> {
                            redeemItem.redeem(count);
                            publishEvent(new RedeemItemRedeemedEvent(
                                    getRedeemId(),
                                    redeemItemId,
                                    count,
                                    userId
                            ));
                        },
                        () -> {
                            throw new NoSuchRedeemItemException("兑换项不存在");
                        });
    }

    @Override
    public void addRedeemItem(RedeemItemInfo info) {
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getName(), info.name()))
                .findFirst()
                .ifPresent(redeemItem -> {
                    throw new DuplicatedRedeemItemNameException("兑换项名称重复");
                });

        RedeemItem item = new RedeemItem();
        item.setId(redeemItemIdGenerator().nextRedeemItemId());
        item.setItemInfo(info.name(), info.description());
        item.setItemType(info.type());
        item.setItemPrice(info.price());
        item.setInventory(info.inventory());

        redeemItems.add(item);
        publishEvent(new RedeemItemAddedEvent(
                getRedeemId(),
                item
        ));
    }

    @Override
    public void removeRedeemItem(IdentifierId<Long> redeemItemId) {
        if (redeemItems.removeIf(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))) {
            publishEvent(new RedeemItemRemovedEvent(
                    getRedeemId(),
                    redeemItemId
            ));
        }
    }

    @Override
    public void modifyRedeemItemBasicInfo(IdentifierId<Long> redeemItemId, String name, String description) {
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .ifPresentOrElse(redeemItem -> {
                            redeemItem.setItemInfo(name, description);
                            publishEvent(new RedeemItemInfoModifiedEvent(
                                    getRedeemId(),
                                    redeemItemId,
                                    name,
                                    description
                            ));
                        },
                        () -> {
                            throw new NoSuchRedeemItemException("兑换项不存在");
                        });
    }

    @Override
    public void modifyRedeemItemInventory(IdentifierId<Long> redeemItemId, RedeemItemInventory inventory) {
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .ifPresentOrElse(redeemItem -> {
                            redeemItem.setInventory(inventory);
                            publishEvent(new RedeemItemInventoryModifiedEvent(
                                    getRedeemId(),
                                    redeemItemId,
                                    inventory
                            ));
                        },
                        () -> {
                            throw new NoSuchRedeemItemException("兑换项不存在");
                        });
    }

    @Override
    public void modifyRedeemItemPrice(IdentifierId<Long> redeemItemId, RedeemItemPrice price) {
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .ifPresentOrElse(redeemItem -> {
                            redeemItem.setItemPrice(price);
                            publishEvent(new RedeemItemPriceModifiedEvent(
                                    getRedeemId(),
                                    redeemItemId,
                                    price
                            ));
                        },
                        () -> {
                            throw new NoSuchRedeemItemException("兑换项不存在");
                        });
    }

    @Override
    public void modifyRedeemItemType(IdentifierId<Long> redeemItemId, RedeemItemType type) {
        redeemItems.stream()
                .filter(redeemItem -> Objects.equals(redeemItem.getId(), redeemItemId))
                .findFirst()
                .ifPresentOrElse(redeemItem -> {
                            redeemItem.setItemType(type);
                            publishEvent(new RedeemItemTypeModifiedEvent(
                                    getRedeemId(),
                                    redeemItemId,
                                    type
                            ));
                        },
                        () -> {
                            throw new NoSuchRedeemItemException("兑换项不存在");
                        });
    }

    @Override
    public void create() {
        publishEvent(new RedeemPoolCreatedEvent(
                this
        ));
    }

    @Override
    public void delete() {
        publishEvent(new RedeemPoolDeletedEvent(
                this
        ));
    }
}
