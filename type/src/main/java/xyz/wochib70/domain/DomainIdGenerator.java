package xyz.wochib70.domain;

/**
 * @author WochiB70
 */
public interface DomainIdGenerator<ID> {

    /**
     * 生成下一个聚合Id
     *
     * @return 聚合Id
     */
    IdentifierId<ID> nextAggregateId(String aggregateName);
}
