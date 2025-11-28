package xyz.wochib70.domain.draw.strategy;

import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.DrawAward;
import xyz.wochib70.domain.draw.DrawItem;
import xyz.wochib70.domain.draw.DrawStrategy;
import xyz.wochib70.domain.draw.InsufficientInventoryException;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Set;

public class RandomDrawStrategy implements DrawStrategy {

    public static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public DrawAward draw(Set<DrawItem> drawItems, UserId userId) {
//        int allWeight = drawItems.stream()
//                .filter(DrawItem::validateInventory)
//                .map(DrawItem::getWeight)
//                .mapToInt(Integer::intValue)
//                .sum();
//        if (allWeight == 0) {
//            throw new InsufficientInventoryException("奖池已经空啦！！");
//        }
//        long[] randomNumbers = new long[allWeight];
//        int index = 0;
//        for (DrawItem drawItem : drawItems) {
//            if (!drawItem.validateInventory()) {
//                continue;
//            }
//            for (int i = 0; i < drawItem.getWeight(); i++) {
//                randomNumbers[index + i] = drawItem.getId().getId();
//            }
//            index += drawItem.getWeight();
//        }
//        int idx = RANDOM.nextInt(allWeight);
//
//        DrawItem drawDrawItem = drawItems.stream()
//                .filter(award -> Objects.equals(award.getId().getId(), randomNumbers[idx]))
//                .findFirst()
//                .orElseThrow(() -> new InsufficientInventoryException("奖池已经空啦！！"));
//        drawDrawItem.receive();
//        return drawDrawItem.getId();
        return null;
    }
}
