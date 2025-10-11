package xyz.wochib70.domain.draw;

import org.junit.jupiter.api.Test;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;

import static org.junit.jupiter.api.Assertions.*;

class DrawPriceTest {

    @Test
    void constructor_shouldCreateDrawPriceWithValidParameters() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = 100;

        // When
        DrawPrice drawPrice = new DrawPrice(currencyId, price);

        // Then
        assertNotNull(drawPrice);
        assertEquals(currencyId, drawPrice.currencyId());
        assertEquals(price, drawPrice.price());
    }

    @Test
    void constructor_shouldThrowException_whenCurrencyIdIsNull() {
        // Given
        IdentifierId<Long> currencyId = null;
        Integer price = 100;

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new DrawPrice(currencyId, price);
        });
        // Actual exception message will be from Objects.requireNonNull
    }

    @Test
    void constructor_shouldThrowException_whenPriceIsNull() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = null;

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new DrawPrice(currencyId, price);
        });
        // Actual exception message will be from Objects.requireNonNull
    }

    @Test
    void constructor_shouldThrowException_whenPriceIsNegative() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new DrawPrice(currencyId, price);
        });
        assertEquals("抽奖价格不能小于0", exception.getMessage());
    }

    @Test
    void equals_shouldReturnTrue_whenSameValues() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        Integer price = 100;
        DrawPrice price1 = new DrawPrice(currencyId, price);
        DrawPrice price2 = new DrawPrice(currencyId, price);

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
        DrawPrice price1 = new DrawPrice(currencyId1, price);
        DrawPrice price2 = new DrawPrice(currencyId2, price);

        // When
        boolean result = price1.equals(price2);

        // Then
        assertFalse(result);
    }

    @Test
    void equals_shouldReturnFalse_whenDifferentPrice() {
        // Given
        IdentifierId<Long> currencyId = new DefaultIdentifierId<>(1L);
        DrawPrice price1 = new DrawPrice(currencyId, 100);
        DrawPrice price2 = new DrawPrice(currencyId, 200);

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
        DrawPrice price1 = new DrawPrice(currencyId, price);
        DrawPrice price2 = new DrawPrice(currencyId, price);

        // When
        int hashCode1 = price1.hashCode();
        int hashCode2 = price2.hashCode();

        // Then
        assertEquals(hashCode1, hashCode2);
    }
}