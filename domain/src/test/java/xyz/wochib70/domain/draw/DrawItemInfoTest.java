package xyz.wochib70.domain.draw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawItemInfoTest {

    @Test
    void constructor_shouldCreateDrawItemInfoWithValidParameters() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 10;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When
        DrawItemInfo drawItemInfo = new DrawItemInfo(name, description, type, weight, inventory);

        // Then
        assertNotNull(drawItemInfo);
        assertEquals(name, drawItemInfo.name());
        assertEquals(description, drawItemInfo.description());
        assertEquals(type, drawItemInfo.type());
        assertEquals(weight, drawItemInfo.weight());
        assertEquals(inventory, drawItemInfo.inventory());
    }

    @Test
    void constructor_shouldThrowException_whenNameIsNull() {
        // Given
        String name = null;
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 10;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的name不能为null或者是空白字符串", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenNameIsEmpty() {
        // Given
        String name = "";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 10;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的name不能为null或者是空白字符串", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsNull() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = null;
        Integer weight = 10;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的type不能null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenWeightIsNull() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = null;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的weight不能为null 或者 小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenWeightIsZero() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 0;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的weight不能为null 或者 小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenWeightIsNegative() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = -1;
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem的weight不能为null 或者 小于等于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenInventoryIsNull() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 10;
        DrawItemInventory inventory = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight, inventory);
        });
        assertEquals("DrawItem inventory不能为null", exception.getMessage());
    }
}