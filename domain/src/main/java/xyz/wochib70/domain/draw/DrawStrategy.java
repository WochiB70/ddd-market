package xyz.wochib70.domain.draw;


import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;

import java.util.Set;

public interface DrawStrategy {


    IdentifierId<Long> draw(Set<DrawItem> drawItem, UserId userId);
}
