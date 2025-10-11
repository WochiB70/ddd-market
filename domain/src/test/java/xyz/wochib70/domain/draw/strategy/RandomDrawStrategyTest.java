package xyz.wochib70.domain.draw.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomDrawStrategyTest {

    private RandomDrawStrategy strategy;
    private Set<DrawItem> drawItems;
    private UserId userId;

    @BeforeEach
    void setUp() {
        strategy = new RandomDrawStrategy();
        userId = new UserId(1L);
        drawItems = new HashSet<>();
        
        // Create draw items with different weights
        DrawItemInventory inventory1 = new DrawItemInventory(DrawInventoryType.LIMITED, 5);
        DrawItem item1 = new DrawItem(new DefaultIdentifierId<>(1L), "Item 1", "Description 1", DrawItemType.COUPON, 10, inventory1);
        
        DrawItemInventory inventory2 = new DrawItemInventory(DrawInventoryType.LIMITED, 5);
        DrawItem item2 = new DrawItem(new DefaultIdentifierId<>(2L), "Item 2", "Description 2", DrawItemType.VIP, 20, inventory2);
        
        DrawItemInventory inventory3 = new DrawItemInventory(DrawInventoryType.LIMITED, 5);
        DrawItem item3 = new DrawItem(new DefaultIdentifierId<>(3L), "Item 3", "Description 3", DrawItemType.VIRTUAL_GOODS, 30, inventory3);
        
        drawItems.add(item1);
        drawItems.add(item2);
        drawItems.add(item3);
    }

    @Test
    void draw_shouldReturnValidAwardId_whenItemsHaveInventory() {
        // When
        IdentifierId<Long> awardId = strategy.draw(drawItems, userId);

        // Then
        assertNotNull(awardId);
        assertTrue(awardId.getId() >= 1 && awardId.getId() <= 3);
    }

    @Test
    void draw_shouldThrowException_whenNoItemsHaveInventory() {
        // Given - Set all inventories to 0
        drawItems.clear();
        DrawItemInventory inventory1 = new DrawItemInventory(DrawInventoryType.LIMITED, 0);
        DrawItem item1 = new DrawItem(new DefaultIdentifierId<>(1L), "Item 1", "Description 1", DrawItemType.COUPON, 10, inventory1);
        
        DrawItemInventory inventory2 = new DrawItemInventory(DrawInventoryType.LIMITED, 0);
        DrawItem item2 = new DrawItem(new DefaultIdentifierId<>(2L), "Item 2", "Description 2", DrawItemType.VIP, 20, inventory2);
        
        drawItems.add(item1);
        drawItems.add(item2);

        // When & Then
        assertThrows(InsufficientInventoryException.class, () -> {
            strategy.draw(drawItems, userId);
        });
    }

    @Test
    void draw_shouldDecreaseInventoryOfWinningItem() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 1);
        DrawItem item = new DrawItem(new DefaultIdentifierId<>(1L), "Item", "Description", DrawItemType.COUPON, 10, inventory);
        Set<DrawItem> singleItemSet = new HashSet<>();
        singleItemSet.add(item);
        
        // Ensure inventory is valid before draw
        assertTrue(item.validateInventory());

        // When
        strategy.draw(singleItemSet, userId);

        // Then
        assertFalse(item.validateInventory());
    }
}