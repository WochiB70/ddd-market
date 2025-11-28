package xyz.wochib70.infrastructure.outbox;

import java.util.List;

public interface OutBoxRepository {


    /**
     * 查询batchSize条数据，并设置状态为处理中
     * 原子性操作
     *
     * @param batchSize 查询的条数
     * @return 查询到的数据 emptyList 如过没有
     */
    List<OutBoxEntity> queryBatchAndSetProcessing(int batchSize);

    /**
     * 保存batchSize条数据
     *
     * @param outBoxEntities 要保存的数据
     */
    void saveBatch(List<OutBoxEntity> outBoxEntities);
}
