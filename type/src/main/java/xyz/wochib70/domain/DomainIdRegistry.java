package xyz.wochib70.domain;


import java.util.Objects;

/**
 * 这是一个妥协文件，由于每个创建事件都需要一个id，但是id生成器是依赖反转由外部注入的。
 * 导致每一个创建事件的地方都需要注入id生成器，导致依赖过多，所以设计这样一个妥协类。
 * 需要外部实现DomainEventIdGenerator, DomainIdGenerator的时候完成配置
 *
 * @author WochiB70
 */
public class DomainIdRegistry {

    public static DomainEventIdGenerator<Long> domainEventIdGenerator;

    public static IdentifierId<Long> nextEventId() {
        Objects.requireNonNull(domainEventIdGenerator, "domainEventIdGenerator不能为null，需要手动设置一个实例到DomainIdRegistry中");
        return domainEventIdGenerator.nextEventId();
    }
}
