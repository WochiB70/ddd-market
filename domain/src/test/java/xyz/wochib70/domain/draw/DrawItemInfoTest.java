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

        // When
        DrawItemInfo drawItemInfo = new DrawItemInfo(name, description, type, weight);

        // Then
        assertNotNull(drawItemInfo);
        assertEquals(name, drawItemInfo.name());
        assertEquals(description, drawItemInfo.description());
        assertEquals(type, drawItemInfo.type());
        assertEquals(weight, drawItemInfo.weight());
    }

    @Test
    void constructor_shouldThrowException_whenNameIsNull() {
        // Given
        String name = null;
        String description = "Test Description";
        DrawItemType type = DrawItemType.COUPON;
        Integer weight = 10;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
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

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
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

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
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

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
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

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
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

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInfo(name, description, type, weight);
        });
        assertEquals("DrawItem的weight不能为null 或者 小于等于0", exception.getMessage());
    }

}