package xyz.wochib70.infrastructure.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OutBoxRepositoryImpl implements OutBoxRepository {

    private final OutBoxDao outBoxDao;

    private final JPAQueryFactory queryFactory;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<OutBoxEntity> queryBatchAndSetProcessing(int batchSize) {
        QOutBoxEntity outBox = QOutBoxEntity.outBoxEntity;
        List<OutBoxEntity> list = queryFactory
                .selectFrom(outBox)
                .where(outBox.type.eq(OutBoxType.READY))
                .orderBy(outBox.createdTime.asc())
                .limit(batchSize)
                .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
                .fetch();

        list.forEach(outBoxEntity -> outBoxEntity.setType(OutBoxType.PROCESSING));
        outBoxDao.saveAll(list);
        return list;
    }

    @Override
    public void saveBatch(List<OutBoxEntity> outBoxEntities) {
        outBoxDao.saveAll(outBoxEntities);
    }
}
