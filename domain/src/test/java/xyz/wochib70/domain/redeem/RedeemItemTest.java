package xyz.wochib70.domain.redeem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;

import static org.junit.jupiter.api.Assertions.*;

class RedeemItemTest {

    private RedeemItem redeemItem;
    private IdentifierId<Long> id;
    private String name = "Test Item";
    private String description = "Test Description";
    private RedeemItemPrice price;
    private RedeemItemType type;

    @BeforeEach
    void setUp() {
        id = new DefaultIdentifierId<>(1L);
        price = new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100);
        type = RedeemItemType.COUPON;
        
        redeemItem = new RedeemItem();
        redeemItem.setId(id);
        redeemItem.setItemInfo(name, description);
        redeemItem.setItemPrice(price);
        redeemItem.setItemType(type);
    }


    @Test
    void setId_shouldUpdateId_whenIdIsValid() {
        // Given
        IdentifierId<Long> newId = new DefaultIdentifierId<>(2L);

        // When
        redeemItem.setId(newId);

        // Then
        assertEquals(newId, redeemItem.getId());
    }

    @Test
    void setId_shouldThrowException_whenIdIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeemItem.setId(null);
        });
        assertEquals("兑换项的id不能为null", exception.getMessage());
    }

    @Test
    void setItemInfo_shouldUpdateNameAndDescription_whenParametersAreValid() {
        // Given
        String newName = "New Name";
        String newDescription = "New Description";

        // When
        redeemItem.setItemInfo(newName, newDescription);

        // Then
        assertEquals(newName, redeemItem.getName());
        assertEquals(newDescription, redeemItem.getDescription());
    }

    @Test
    void setItemInfo_shouldThrowException_whenNameIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeemItem.setItemInfo(null, "New Description");
        });
        assertEquals("兑换项的名称不能为null或空", exception.getMessage());
    }

    @Test
    void setItemInfo_shouldThrowException_whenNameIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeemItem.setItemInfo("", "New Description");
        });
        assertEquals("兑换项的名称不能为null或空", exception.getMessage());
    }

    @Test
    void setItemType_shouldUpdateType_whenTypeIsValid() {
        // Given
        RedeemItemType newType = RedeemItemType.VIP;

        // When
        redeemItem.setItemType(newType);

        // Then
        assertEquals(newType, redeemItem.getType());
    }

    @Test
    void setItemType_shouldThrowException_whenTypeIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeemItem.setItemType(null);
        });
        assertEquals("兑换项的类型不能为null", exception.getMessage());
    }

    @Test
    void setItemPrice_shouldUpdatePrice_whenPriceIsValid() {
        // Given
        RedeemItemPrice newPrice = new RedeemItemPrice(new DefaultIdentifierId<>(2L), 200);

        // When
        redeemItem.setItemPrice(newPrice);

        // Then
        assertEquals(newPrice, redeemItem.getPrice());
    }

    @Test
    void setItemPrice_shouldThrowException_whenPriceIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeemItem.setItemPrice(null);
        });
        assertEquals("兑换项的价格不能为null", exception.getMessage());
    }





    @Test
    void equals_shouldReturnTrue_whenSameIdAndName() {
        // Given
        RedeemItem otherItem = new RedeemItem();
        otherItem.setId(id);
        otherItem.setItemInfo(name, "Other Description");
        otherItem.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(2L), 50));
        otherItem.setItemType(RedeemItemType.VIP);

        // When
        boolean result = redeemItem.equals(otherItem);

        // Then
        assertTrue(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentId() {
        // Given
        RedeemItem otherItem = new RedeemItem();
        otherItem.setId(new DefaultIdentifierId<>(2L));
        otherItem.setItemInfo(name, description);
        otherItem.setItemPrice(price);
        otherItem.setItemType(type);

        // When
        boolean result = redeemItem.equals(otherItem);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentName() {
        // Given
        RedeemItem otherItem = new RedeemItem();
        otherItem.setId(id);
        otherItem.setItemInfo("Other Name", description);
        otherItem.setItemPrice(price);
        otherItem.setItemType(type);

        // When
        boolean result = redeemItem.equals(otherItem);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithNull() {
        // When
        boolean result = redeemItem.equals(null);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenComparedWithDifferentClass() {
        // When
        boolean result = redeemItem.equals(new Object());

        // Then
        assertFalse(result);
    }

    @Test
    void hashCode_shouldReturnSameValue_forItemsWithSameIdAndName() {
        // Given
        RedeemItem otherItem = new RedeemItem();
        otherItem.setId(id);
        otherItem.setItemInfo(name, "Other Description");
        otherItem.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(2L), 50));
        otherItem.setItemType(RedeemItemType.VIP);

        // When
        int hashCode1 = redeemItem.hashCode();
        int hashCode2 = otherItem.hashCode();

        // Then
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_shouldReturnDifferentValue_forItemsWithDifferentId() {
        // Given
        RedeemItem otherItem = new RedeemItem();
        otherItem.setId(new DefaultIdentifierId<>(2L));
        otherItem.setItemInfo(name, description);
        otherItem.setItemPrice(price);
        otherItem.setItemType(type);

        // When
        int hashCode1 = redeemItem.hashCode();
        int hashCode2 = otherItem.hashCode();

        // Then
        assertNotEquals(hashCode1, hashCode2);
    }
}