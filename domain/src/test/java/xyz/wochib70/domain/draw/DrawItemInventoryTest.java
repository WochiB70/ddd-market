package xyz.wochib70.domain.draw;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.*;

class DrawItemInventoryTest {

    @Test
    void constructor_shouldCreateInventoryWithValidParameters() {
        // Given
        DrawInventoryType type = DrawInventoryType.LIMITED;
        Integer surplus = 10;

        // When
        DrawItemInventory inventory = new DrawItemInventory(type, surplus);

        // Then
        assertNotNull(inventory);
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsNull() {
        // Given
        DrawInventoryType type = null;
        Integer surplus = 10;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInventory(type, surplus);
        });
        assertEquals("奖品库存类型不能为null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenSurplusIsNull() {
        // Given
        DrawInventoryType type = DrawInventoryType.LIMITED;
        Integer surplus = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInventory(type, surplus);
        });
        assertEquals("奖品库存不能为null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenLimitedTypeAndSurplusIsNegative() {
        // Given
        DrawInventoryType type = DrawInventoryType.LIMITED;
        Integer surplus = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawItemInventory(type, surplus);
        });
        assertEquals("奖品库存不能小于0", exception.getMessage());
    }

    @Test
    void constructor_shouldNotThrowException_whenInfiniteTypeAndSurplusIsNegative() {
        // Given
        DrawInventoryType type = DrawInventoryType.INFINITE;
        Integer surplus = -1;

        // When
        DrawItemInventory inventory = new DrawItemInventory(type, surplus);

        // Then
        assertNotNull(inventory);
    }

    @Test
    void receive_shouldNotThrowException_whenTypeIsInfinite() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.INFINITE, 10);

        // When & Then
        assertDoesNotThrow(() -> {
            inventory.receive();
        });
    }

    @Test
    void receive_shouldDecreaseSurplus_whenTypeIsLimitedAndHasInventory() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);

        // When
        inventory.receive();

        // Then
        // We can't directly access surplus, but we can test the behavior
        assertTrue(inventory.validInventory());
        // Receive 4 more times
        inventory.receive();
        inventory.receive();
        inventory.receive();
        inventory.receive();
        // Now inventory should be empty
        assertFalse(inventory.validInventory());
    }

    @Test
    void receive_shouldThrowException_whenTypeIsLimitedAndNoInventory() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 0);

        // When & Then
        InsufficientInventoryException exception = assertThrows(InsufficientInventoryException.class, () -> {
            inventory.receive();
        });
        assertEquals("奖品库存不足", exception.getMessage());
    }

    @Test
    void validInventory_shouldReturnTrue_whenTypeIsInfinite() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.INFINITE, 0);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertTrue(result);
    }

    @Test
    void validInventory_shouldReturnTrue_whenTypeIsLimitedAndHasInventory() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 1);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertTrue(result);
    }

    @Test
    void validInventory_shouldReturnFalse_whenTypeIsLimitedAndNoInventory() {
        // Given
        DrawItemInventory inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 0);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertFalse(result);
    }
}