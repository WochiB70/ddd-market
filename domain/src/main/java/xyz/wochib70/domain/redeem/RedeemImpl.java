package xyz.wochib70.domain.redeem;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.redeem.events.*;
import xyz.wochib70.domain.utils.ParameterUtil;

import java.util.Objects;
import java.util.Set;

import static xyz.wochib70.domain.redeem.RedeemDomainRegistry.redeemItemIdGenerator;

@Getter
@Setter
public non-sealed class RedeemImpl extends AbstractAggregate<Long> implements Redeem {

    private IdentifierId<Long> activityId;

    private String name;

    private Set<RedeemItem> redeemItems;

    private RedeemParticipateScope scope;


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
        ParameterUtil.requireNonBlank(name, "名称不能为空");
        if (!Objects.equals(this.name, name)) {
            this.name = name;
            publishEvent(new RedeemNameModifiedEvent(
                    getRedeemId(),
                    name
            ));
        }
    }

    @Override
    public void modifyParticipateScope(RedeemParticipateScope scope) {
        ParameterUtil.requireNonNull(scope, "参与范围不能为null");
        if (!Objects.equals(this.scope, scope)) {
            this.scope = scope;
            publishEvent(new RedeemParticipateScopeModifiedEvent(
                    getRedeemId(),
                    scope
            ));
        }
    }

    @Override
    public RedeemItemPrice getRedeemItemPriceOrThrow(IdentifierId<Long> redeemItemId) {
        return findRedeemItemOrThrow(redeemItemId).getPrice();
    }

    @Override
    public void redeem(IdentifierId<Long> redeemItemId, int count, UserId userId) {
        if (count <= 0) {
            throw new IllegalArgumentException("兑换数量不能小于0");
        }
        findRedeemItemOrThrow(redeemItemId);
        publishEvent(new RedeemItemRedeemedEvent(
                getRedeemId(),
                redeemItemId,
                count,
                userId
        ));
    }

    @Override
    public IdentifierId<Long> addRedeemItem(RedeemItemInfo info) {
        ParameterUtil.requireNonNull(info, "RedeemItemInfo不能为null");
        checkDuplicateRedeemItemName(info.name());

        RedeemItem item = new RedeemItem();
        item.setId(redeemItemIdGenerator().nextRedeemItemId());
        item.setItemInfo(info.name(), info.description());
        item.setItemType(info.type());
        item.setItemPrice(info.price());

        redeemItems.add(item);
        publishEvent(new RedeemItemAddedEvent(
                getRedeemId(),
                item
        ));
        return item.getId();
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
        ParameterUtil.requireNonBlank(name, "名称不能为空");
        ParameterUtil.requireExpression(Objects.nonNull(description) && description.length() > 50, "描述不能为空");

        RedeemItem redeemItem = findRedeemItemOrThrow(redeemItemId);
        redeemItem.setItemInfo(name, description);
        publishEvent(new RedeemItemInfoModifiedEvent(
                getRedeemId(),
                redeemItemId,
                name,
                description
        ));
    }

    @Override
    public void modifyRedeemItemPrice(IdentifierId<Long> redeemItemId, RedeemItemPrice price) {
        ParameterUtil.requireNonNull(price, "RedeemItemPrice不能为null");

        RedeemItem redeemItem = findRedeemItemOrThrow(redeemItemId);
        redeemItem.setItemPrice(price);
        publishEvent(new RedeemItemPriceModifiedEvent(
                getRedeemId(),
                redeemItemId,
                price
        ));
    }

    @Override
    public void modifyRedeemItemType(IdentifierId<Long> redeemItemId, RedeemItemType type) {
        ParameterUtil.requireNonNull(type, "RedeemItemType不能为null");

        RedeemItem redeemItem = findRedeemItemOrThrow(redeemItemId);
        redeemItem.setItemType(type);
        publishEvent(new RedeemItemTypeModifiedEvent(
                getRedeemId(),
                redeemItemId,
                type
        ));
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

    /**
     * 根据ID查找RedeemItem，如果不存在则抛出异常
     *
     * @param redeemItemId 兑换项ID
     * @return RedeemItem
     * @throws NoSuchRedeemItemException 如果兑换项不存在
     */
    private RedeemItem findRedeemItemOrThrow(IdentifierId<Long> redeemItemId) {
        return redeemItems.stream()
                .filter(item -> Objects.equals(item.getId(), redeemItemId))
                .findFirst()
                .orElseThrow(() -> new NoSuchRedeemItemException("兑换项不存在"));
    }

    /**
     * 检查是否存在重复的兑换项名称
     *
     * @param name 要检查的名称
     * @throws DuplicatedRedeemItemNameException 如果存在重复名称
     */
    private void checkDuplicateRedeemItemName(String name) {
        redeemItems.stream()
                .filter(item -> Objects.equals(item.getName(), name))
                .findFirst()
                .ifPresent(item -> {
                    throw new DuplicatedRedeemItemNameException("兑换项名称重复");
                });
    }
}
