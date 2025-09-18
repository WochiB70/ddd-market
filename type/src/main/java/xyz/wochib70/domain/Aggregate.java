package xyz.wochib70.domain;

import java.util.Collection;

/**
 * @author WochiB70
 */
public interface Aggregate<ID, EventID> {


    /**
     * 获取聚合根的标识符
     *
     * @return identifierId
     */
    IdentifierId<ID> identifierId();

    /**
     * @return 领域内发生的事件，按照发生顺序
     */
    Collection<? super AggregateEvent<ID, EventID>> getEvents();


    default void create() {
        throw new UnsupportedOperationException("当前聚合不支持当前操作");
    }

    default void delete(UserId userId) {
        throw new UnsupportedOperationException("当前聚合不支持当前操作");
    }

}
