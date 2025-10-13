package xyz.wochib70.domain.redeem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedeemItemInventoryTest {

    @Test
    void constructor_shouldCreateInventoryWithValidParameters() {
        // Given
        RedeemItemInventoryType type = RedeemItemInventoryType.LIMITED;
        Integer validCount = 10;

        // When
        RedeemItemInventory inventory = new RedeemItemInventory(type, validCount);

        // Then
        assertNotNull(inventory);
        assertEquals(type, inventory.getType());
        assertEquals(validCount, inventory.getValidCount());
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsNull() {
        // Given
        RedeemItemInventoryType type = null;
        Integer validCount = 10;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInventory(type, validCount);
        });
        assertEquals("兑换项的库存类型不能为null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsNotInfiniteAndValidCountIsNull() {
        // Given
        RedeemItemInventoryType type = RedeemItemInventoryType.LIMITED;
        Integer validCount = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInventory(type, validCount);
        });
        assertEquals("当前的库存类型不是[INFINITE], 库存数量不能为null或是小于0", exception.getMessage());
    }


    @Test
    void constructor_shouldThrowException_whenTypeIsNotInfiniteAndValidCountIsNegative() {
        // Given
        RedeemItemInventoryType type = RedeemItemInventoryType.LIMITED;
        Integer validCount = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInventory(type, validCount);
        });
        assertEquals("当前的库存类型不是[INFINITE], 库存数量不能为null或是小于0", exception.getMessage());
    }

    @Test
    void constructor_shouldNotThrowException_whenTypeIsInfiniteAndValidCountIsNull() {
        // Given
        RedeemItemInventoryType type = RedeemItemInventoryType.INFINITE;
        Integer validCount = null;

        // When
        RedeemItemInventory inventory = new RedeemItemInventory(type, validCount);

        // Then
        assertNotNull(inventory);
        assertEquals(type, inventory.getType());
        assertEquals(validCount, inventory.getValidCount());
    }

    @Test
    void redeem_shouldDecreaseValidCount() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.LIMITED, 5);

        // When
        inventory.redeem(2);

        // Then
        assertEquals(3, inventory.getValidCount());
    }

    @Test
    void redeem_shouldThrowException_whenCountExceedsValidCount() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.LIMITED, 5);

        // When & Then
        InsufficientRedeemItemInventoryException exception = assertThrows(InsufficientRedeemItemInventoryException.class, () -> {
            inventory.redeem(6);
        });
        assertEquals("库存不足", exception.getMessage());
    }

    @Test
    void redeem_shouldNotThrowException_whenTypeIsInfinite() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.INFINITE, 5);

        // When & Then
        assertDoesNotThrow(() -> {
            inventory.redeem(10);
        });
    }

    @Test
    void validInventory_shouldReturnTrue_whenTypeIsInfinite() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.INFINITE, 0);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertTrue(result);
    }

    @Test
    void validInventory_shouldReturnTrue_whenTypeIsLimitedAndHasInventory() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.LIMITED, 1);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertTrue(result);
    }

    @Test
    void validInventory_shouldReturnFalse_whenTypeIsLimitedAndNoInventory() {
        // Given
        RedeemItemInventory inventory = new RedeemItemInventory(RedeemItemInventoryType.LIMITED, 0);

        // When
        boolean result = inventory.validInventory();

        // Then
        assertFalse(result);
    }
}