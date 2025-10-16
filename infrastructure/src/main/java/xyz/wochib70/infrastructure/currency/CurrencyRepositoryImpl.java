package xyz.wochib70.infrastructure.currency;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.currency.*;

@AllArgsConstructor
@Component
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final CurrencyDao currencyDao;

    @Override
    public void save(Currency currency) {
        CurrencyEntity entity = toEntity(currency);
        currencyDao.save(entity);
    }

    @Override
    public Currency findByIdOrThrow(IdentifierId<Long> id) {
        return currencyDao.queryById(id.getId())
                .map(this::toDomain)
                .orElseThrow(() -> new NoSuchCurrencyException("货币不存在"));
    }

    private CurrencyEntity toEntity(Currency currency) {
        //实现聚合转化到Entity
        CurrencyImpl impl = (CurrencyImpl) currency;
        CurrencyEntity entity = new CurrencyEntity();
        entity.setId(impl.getCurrencyId().getId());
        entity.setName(impl.getInfo().name());
        entity.setDescription(impl.getInfo().description());
        entity.setStatus(impl.getStatus());
        entity.setReferenceCount(impl.getReferenceCount());
        return entity;
    }

    private Currency toDomain(CurrencyEntity entity) {
        //实现Entity转化成聚合
        CurrencyImpl currency = new CurrencyImpl(new DefaultIdentifierId<>(entity.getId()));
        currency.setInfo(new CurrencyInfo(entity.getName(), entity.getDescription()));
        currency.setStatus(entity.getStatus());
        currency.setReferenceCount(entity.getReferenceCount());
        return currency;
    }
}
