package xyz.wochib70.domain.currency;

import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.AbstractAggregate;
import xyz.wochib70.domain.DomainException;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.events.CurrencyCreatedEvent;
import xyz.wochib70.domain.currency.events.CurrencyDeactivatedEvent;
import xyz.wochib70.domain.currency.events.CurrencyEnabledEvent;
import xyz.wochib70.domain.currency.events.CurrencyInfoModifiedEvent;

import java.util.Objects;

@Getter
@Setter
public non-sealed class CurrencyImpl extends AbstractAggregate<Long> implements Currency {

    private CurrencyInfo info;

    private CurrencyStatus status;

    private Integer referenceCount;


    public CurrencyImpl(IdentifierId<Long> identifierId) {
        super(identifierId);
    }

    @Override
    public IdentifierId<Long> getCurrencyId() {
        return identifierId();
    }

    @Override
    public void modifyInfo(CurrencyInfo info) {
        if (!Objects.equals(this.info, info)) {
            this.info = info;
            publishEvent(new CurrencyInfoModifiedEvent(
                    getCurrencyId(),
                    info
            ));
        }
    }

    @Override
    public void enable() {
        if (Objects.equals(status, CurrencyStatus.INVALID)) {
            status = CurrencyStatus.VALID;
            publishEvent(new CurrencyEnabledEvent(
                    getCurrencyId()
            ));
        }
    }

    @Override
    public void deactivate() {
        if (Objects.equals(status, CurrencyStatus.INVALID)) {
            return;
        }
        if (referenceCount > 0) {
            throw new DomainException("不能停用货币：[id： " + getCurrencyId() + "， name " + info.name() + "], 存在其他任务或是活动引用此币种");
        }
        status = CurrencyStatus.INVALID;
        publishEvent(new CurrencyDeactivatedEvent(
                getCurrencyId()
        ));
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(status, CurrencyStatus.VALID);
    }

    @Override
    public void increaseReferenceCount() {
        referenceCount++;
    }

    @Override
    public void decreaseReferenceCount() {
        referenceCount--;
    }

    @Override
    public void create() {
        publishEvent(new CurrencyCreatedEvent(
                getCurrencyId(),
                info.name(),
                info.description(),
                status,
                referenceCount
        ));
    }
}
