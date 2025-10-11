package xyz.wochib70.domain.draw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;

import static org.junit.jupiter.api.Assertions.*;

class DrawItemTest {

    private DrawItem drawItem;
    private IdentifierId<Long> id;
    private String name = "Test Item";
    private String description = "Test Description";
    private DrawItemType type = DrawItemType.COUPON;
    private Integer weight = 10;
    private DrawItemInventory inventory;

    @BeforeEach
    void setUp() {
        id = new DefaultIdentifierId<>(1L);
        inventory = new DrawItemInventory(DrawInventoryType.LIMITED, 5);
        drawItem = new DrawItem(id, name, description, type, weight, inventory);
    }

    @Test
    void constructor_shouldCreateDrawItemWithValidParameters() {
        // When
        DrawItem item = new DrawItem(id, name, description, type, weight, inventory);

        // Then
        assertNotNull(item);
        assertEquals(id, item.getId());
        assertEquals(name, item.getName());
        assertEquals(description, item.getDescription());
        assertEquals(type, item.getType());
        assertEquals(weight, item.getWeight());
        assertEquals(inventory, item.getDrawItemInventory());
    }

    @Test
    void receive_shouldDelegateToInventory() {
        // When & Then
        assertDoesNotThrow(() -> {
            drawItem.receive();
        });
    }

    @Test
    void validateInventory_shouldDelegateToInventory() {
        // When
        boolean result = drawItem.validateInventory();

        // Then
        assertTrue(result);
    }

    @Test
    void setWeight_shouldUpdateWeight_whenWeightIsValid() {
        // Given
        Integer newWeight = 20;

        // When
        drawItem.setWeight(newWeight);

        // Then
        assertEquals(newWeight, drawItem.getWeight());
    }

    @Test
    void setWeight_shouldThrowException_whenWeightIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drawItem.setWeight(null);
        });
        assertEquals("奖品权重不能为null或者小于0", exception.getMessage());
    }

    @Test
    void setWeight_shouldThrowException_whenWeightIsNegative() {
        // Given
        Integer negativeWeight = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drawItem.setWeight(negativeWeight);
        });
        assertEquals("奖品权重不能为null或者小于0", exception.getMessage());
    }

    @Test
    void setInventory_shouldUpdateInventory_whenInventoryIsValid() {
        // Given
        DrawItemInventory newInventory = new DrawItemInventory(DrawInventoryType.INFINITE, 0);

        // When
        drawItem.setInventory(newInventory);

        // Then
        assertEquals(newInventory, drawItem.getDrawItemInventory());
    }

    @Test
    void setInventory_shouldThrowException_whenInventoryIsNull() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            drawItem.setInventory(null);
        });
        assertEquals("奖品数量不能为空", exception.getMessage());
    }

    @Test
    void equals_shouldReturnTrue_whenSameName() {
        // Given
        IdentifierId<Long> otherId = new DefaultIdentifierId<>(2L);
        DrawItem otherItem = new DrawItem(otherId, name, "Other Description", DrawItemType.VIP, 5, 
            new DrawItemInventory(DrawInventoryType.LIMITED, 3));

        // When
        boolean result = drawItem.equals(otherItem);

        // Then
        assertTrue(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentName() {
        // Given
        IdentifierId<Long> otherId = new DefaultIdentifierId<>(2L);
        DrawItem otherItem = new DrawItem(otherId, "Other Name", description, type, weight, inventory);

        // When
        boolean result = drawItem.equals(otherItem);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithNull() {
        // When
        boolean result = drawItem.equals(null);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithDifferentClass() {
        // When
        boolean result = drawItem.equals(new Object());

        // Then
        assertFalse(result);
    }

    @Test
    void hashCode_shouldReturnSameValue_forItemsWithSameName() {
        // Given
        IdentifierId<Long> otherId = new DefaultIdentifierId<>(2L);
        DrawItem otherItem = new DrawItem(otherId, name, "Other Description", DrawItemType.VIP, 5,
            new DrawItemInventory(DrawInventoryType.LIMITED, 3));

        // When
        int hashCode1 = drawItem.hashCode();
        int hashCode2 = otherItem.hashCode();

        // Then
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_shouldReturnDifferentValue_forItemsWithDifferentName() {
        // Given
        IdentifierId<Long> otherId = new DefaultIdentifierId<>(2L);
        DrawItem otherItem = new DrawItem(otherId, "Other Name", description, type, weight, inventory);

        // When
        int hashCode1 = drawItem.hashCode();
        int hashCode2 = otherItem.hashCode();

        // Then
        assertNotEquals(hashCode1, hashCode2);
    }
}