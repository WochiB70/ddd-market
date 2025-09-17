package xyz.wochib70.domain;

/**
 * @author WochiB70
 */
public interface DomainEventIdGenerator<ID> {

    /**
     * 生产下一个事件Id
     *
     * @return IdentifierId
     */
    IdentifierId<ID> nextEventId();
}
