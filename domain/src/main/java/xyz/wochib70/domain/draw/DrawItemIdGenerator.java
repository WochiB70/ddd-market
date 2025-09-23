package xyz.wochib70.domain.draw;

import xyz.wochib70.domain.IdentifierId;

public interface DrawItemIdGenerator {

    IdentifierId<Long> nextAwardId();
}
