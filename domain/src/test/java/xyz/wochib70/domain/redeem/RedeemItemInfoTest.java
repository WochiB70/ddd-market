package xyz.wochib70.domain.redeem;

import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;

import static org.junit.jupiter.api.Assertions.*;

class RedeemItemInfoTest {

    @Test
    void constructor_shouldCreateRedeemItemInfoWithValidParameters() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        RedeemItemType type = RedeemItemType.COUPON;
        RedeemItemPrice price = new RedeemItemPrice( new DefaultIdentifierId<>(1L), 100);

        // When
        RedeemItemInfo redeemItemInfo = new RedeemItemInfo(name, description, type, price);

        // Then
        assertNotNull(redeemItemInfo);
        assertEquals(name, redeemItemInfo.name());
        assertEquals(description, redeemItemInfo.description());
        assertEquals(type, redeemItemInfo.type());
        assertEquals(price, redeemItemInfo.price());
    }

    @Test
    void constructor_shouldThrowException_whenNameIsNull() {
        // Given
        String name = null;
        String description = "Test Description";
        RedeemItemType type = RedeemItemType.COUPON;
        RedeemItemPrice price = new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInfo(name, description, type, price);
        });
        assertEquals("兑换项的名称不能为null或空", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenNameIsBlank() {
        // Given
        String name = "";
        String description = "Test Description";
        RedeemItemType type = RedeemItemType.COUPON;
        RedeemItemPrice price = new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInfo(name, description, type, price);
        });
        assertEquals("兑换项的名称不能为null或空", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenTypeIsNull() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        RedeemItemType type = null;
        RedeemItemPrice price = new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInfo(name, description, type, price);
        });
        assertEquals("兑换项的类型不能为null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenPriceIsNull() {
        // Given
        String name = "Test Item";
        String description = "Test Description";
        RedeemItemType type = RedeemItemType.COUPON;
        RedeemItemPrice price = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemInfo(name, description, type, price);
        });
        assertEquals("兑换项的价格不能为null", exception.getMessage());
    }

}