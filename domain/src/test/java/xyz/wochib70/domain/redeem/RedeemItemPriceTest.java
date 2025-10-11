package xyz.wochib70.domain.redeem;

import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;

import static org.junit.jupiter.api.Assertions.*;

class RedeemItemPriceTest {

    @Test
    void constructor_shouldCreateRedeemItemPriceWithValidParameters() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = 100;

        // When
        RedeemItemPrice redeemItemPrice = new RedeemItemPrice(currencyId, price);

        // Then
        assertNotNull(redeemItemPrice);
        assertEquals(currencyId, redeemItemPrice.currencyId());
        assertEquals(price, redeemItemPrice.price());
    }

    @Test
    void constructor_shouldThrowException_whenCurrencyIdIsNull() {
        // Given
        IdentifierId<Long> currencyId = null;
        Integer price = 100;

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new RedeemItemPrice(currencyId, price);
        });
        assertEquals("货币Id不能为null", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenPriceIsNull() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemPrice(currencyId, price);
        });
        assertEquals("价格不能为null或者小于0", exception.getMessage());
    }

    @Test
    void constructor_shouldThrowException_whenPriceIsNegative() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new RedeemItemPrice(currencyId, price);
        });
        assertEquals("价格不能为null或者小于0", exception.getMessage());
    }

    @Test
    void equals_shouldReturnTrue_whenSameValues() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = 100;
        RedeemItemPrice price1 = new RedeemItemPrice(currencyId, price);
        RedeemItemPrice price2 = new RedeemItemPrice(currencyId, price);

        // When
        boolean result = price1.equals(price2);

        // Then
        assertTrue(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentCurrencyId() {
        // Given
        IdentifierId<Long> currencyId1 = new DefaultIdentifierId<>(1L);
        IdentifierId<Long> currencyId2 = new DefaultIdentifierId<>(2L);
        Integer price = 100;
        RedeemItemPrice price1 = new RedeemItemPrice(currencyId1, price);
        RedeemItemPrice price2 = new RedeemItemPrice(currencyId2, price);

        // When
        boolean result = price1.equals(price2);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentPrice() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        RedeemItemPrice price1 = new RedeemItemPrice(currencyId, 100);
        RedeemItemPrice price2 = new RedeemItemPrice(currencyId, 200);

        // When
        boolean result = price1.equals(price2);

        // Then
        assertFalse(result);
    }

    @Test
    void hashCode_shouldReturnSameValue_forSameValues() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = 100;
        RedeemItemPrice price1 = new RedeemItemPrice(currencyId, price);
        RedeemItemPrice price2 = new RedeemItemPrice(currencyId, price);

        // When
        int hashCode1 = price1.hashCode();
        int hashCode2 = price2.hashCode();

        // Then
        assertEquals(hashCode1, hashCode2);
    }
}