package xyz.wochib70.domain.draw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.draw.events.*;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrawPoolImplTest extends AggregateTestBase {

    private DrawPoolImpl drawPool;
    private static final Long DRAW_POOL_ID = 1L;
    private static final Long ACTIVITY_ID = 2L;

    @BeforeEach
    void setUp() {
        drawPool = new DrawPoolImpl(new DefaultIdentifierId<>(DRAW_POOL_ID));
        drawPool.setActivityId(new DefaultIdentifierId<>(ACTIVITY_ID));
        drawPool.setDrawItems(new HashSet<>());
        drawPool.setName("Test Draw Pool");
        drawPool.setStrategyType(DrawStrategyType.RANDOM);
        drawPool.setDrawPrice(new DrawPrice(new DefaultIdentifierId<>(3L), 100));
    }

    @Test
    void getDrawPoolId_shouldReturnCorrectId() {
        // When
        IdentifierId<Long> drawPoolId = drawPool.getDrawPoolId();

        // Then
        assertEquals(DRAW_POOL_ID, drawPoolId.getId());
    }

    @Test
    void draw_shouldReturnRewardAndPublishEvent() {
        // Given
        UserId userId = new UserId(1L);

        // Mock the domain registry for award id generator
        try (MockedStatic<DrawDomainRegistry> mockedRegistry = mockStatic(DrawDomainRegistry.class)) {
            DrawItemIdGenerator idGenerator = mock(DrawItemIdGenerator.class);
            AtomicLong counter = new AtomicLong(1);
            when(idGenerator.nextAwardId()).thenReturn(new DefaultIdentifierId<>(counter.incrementAndGet()));
            mockedRegistry.when(DrawDomainRegistry::awardIdGenerator).thenReturn(idGenerator);

            // Add items to the pool
            DrawItemInfo itemInfo = new DrawItemInfo("Test Item", "Description", DrawItemType.COUPON, 10);
            HashSet<DrawItem> items = new HashSet<>();
            items.add(new DrawItem(
                    new DefaultIdentifierId<>(1L),
                    itemInfo.name(),
                    itemInfo.description(),
                    itemInfo.type(),
                    itemInfo.weight()
            ));
            drawPool.setDrawItems(items);
        }

        // Clear events from addDrawItem


        // When
        Reward reward = drawPool.draw(userId);

        // Then
        assertNotNull(reward);
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(AwardReceivedEvent.class, drawPool.getEvents().getFirst());
    }

    @Test
    void modifyDrawPoolName_shouldUpdateNameAndPublishEvent_whenNameIsValidAndDifferent() {
        // Given
        String newName = "New Pool Name";

        // When
        drawPool.modifyDrawPoolName(newName);

        // Then
        assertEquals(newName, drawPool.getName());
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(DrawPoolNameModifiedEvent.class, drawPool.getEvents().getFirst());

        DrawPoolNameModifiedEvent event = (DrawPoolNameModifiedEvent) drawPool.getEvents().getFirst();
        assertEquals(DRAW_POOL_ID, event.getDrawPoolId().getId());
        assertEquals(newName, event.getName());
    }

    @Test
    void modifyDrawPoolName_shouldThrowException_whenNameIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drawPool.modifyDrawPoolName(null);
        });
        assertEquals("名称不能为null或空", exception.getMessage());
    }

    @Test
    void modifyDrawPoolName_shouldThrowException_whenNameIsBlank() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drawPool.modifyDrawPoolName("");
        });
        assertEquals("名称不能为null或空", exception.getMessage());
    }

    @Test
    void modifyDrawPoolName_shouldNotPublishEvent_whenNameIsSame() {
        // Given
        String sameName = drawPool.getName();

        // When
        drawPool.modifyDrawPoolName(sameName);

        // Then
        assertEquals(0, drawPool.getEvents().size());
    }

    @Test
    void modifyDrawStrategy_shouldUpdateStrategyAndPublishEvent_whenStrategyIsDifferent() {
        // Prepare
        drawPool.setStrategyType(null);
        // Given
        DrawStrategyType newStrategy = DrawStrategyType.RANDOM;

        // When
        drawPool.modifyDrawStrategy(newStrategy);

        // Then
        assertEquals(newStrategy, drawPool.getStrategyType());
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(DrawStrategyModifiedEvent.class, drawPool.getEvents().getFirst());

        DrawStrategyModifiedEvent event = (DrawStrategyModifiedEvent) drawPool.getEvents().getFirst();
        assertEquals(DRAW_POOL_ID, event.getDrawPoolId().getId());
        assertEquals(newStrategy, event.getDrawStrategyType());
    }

    @Test
    void modifyDrawStrategy_shouldNotPublishEvent_whenStrategyIsSame() {
        // Given
        DrawStrategyType sameStrategy = drawPool.getStrategyType();

        // When
        drawPool.modifyDrawStrategy(sameStrategy);

        // Then
        assertEquals(0, drawPool.getEvents().size());
    }

    @Test
    void modifyDrawPrice_shouldUpdatePriceAndPublishEvent_whenPriceIsDifferent() {
        // Given
        DrawPrice newPrice = new DrawPrice(new DefaultIdentifierId<>(4L), 200);

        // When
        drawPool.modifyDrawPrice(newPrice);

        // Then
        assertEquals(newPrice, drawPool.getDrawPrice());
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(DrawPoolPriceModifiedEvent.class, drawPool.getEvents().getFirst());

        DrawPoolPriceModifiedEvent event = (DrawPoolPriceModifiedEvent) drawPool.getEvents().getFirst();
        assertEquals(DRAW_POOL_ID, event.getDrawPoolId().getId());
        assertEquals(newPrice, event.getPrice());
    }

    @Test
    void modifyDrawPrice_shouldThrowException_whenPriceIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drawPool.modifyDrawPrice(null);
        });
        // Exception from Objects.requireNonNull
    }

    @Test
    void modifyDrawPrice_shouldNotPublishEvent_whenPriceIsSame() {
        // Given
        DrawPrice samePrice = drawPool.getDrawPrice();

        // When
        drawPool.modifyDrawPrice(samePrice);

        // Then
        assertEquals(0, drawPool.getEvents().size());
    }

    @Test
    void addDrawItem_shouldAddItemAndPublishEvent_whenItemNameDoesNotExist() {
        // Given
        DrawItemInfo itemInfo = new DrawItemInfo("New Item", "Description", DrawItemType.COUPON, 10);

        // Mock the domain registry for award id generator
        try (MockedStatic<DrawDomainRegistry> mockedRegistry = mockStatic(DrawDomainRegistry.class)) {
            DrawItemIdGenerator idGenerator = mock(DrawItemIdGenerator.class);
            when(idGenerator.nextAwardId()).thenReturn(new DefaultIdentifierId<>(1L));
            mockedRegistry.when(DrawDomainRegistry::awardIdGenerator).thenReturn(idGenerator);

            // When
            drawPool.addDrawItem(itemInfo);

            // Then
            assertEquals(1, drawPool.getDrawItems().size());
            assertEquals(1, drawPool.getEvents().size());
            assertInstanceOf(DrawItemAddedEvent.class, drawPool.getEvents().getFirst());
        }
    }

    @Test
    void addDrawItem_shouldThrowException_whenItemNameAlreadyExists() {
        // Given
        DrawItemInfo itemInfo1 = new DrawItemInfo("Existing Item", "Description", DrawItemType.COUPON, 10);
        DrawItemInfo itemInfo2 = new DrawItemInfo("Existing Item", "Another Description", DrawItemType.VIP, 20);

        // Mock the domain registry for award id generator
        try (MockedStatic<DrawDomainRegistry> mockedRegistry = mockStatic(DrawDomainRegistry.class)) {
            DrawItemIdGenerator idGenerator = mock(DrawItemIdGenerator.class);
            when(idGenerator.nextAwardId()).thenReturn(new DefaultIdentifierId<>(1L));
            mockedRegistry.when(DrawDomainRegistry::awardIdGenerator).thenReturn(idGenerator);

            // Add first item
            drawPool.addDrawItem(itemInfo1);

            // When & Then
            DuplicateAwardException exception = assertThrows(DuplicateAwardException.class, () -> {
                drawPool.addDrawItem(itemInfo2);
            });
            assertEquals("Award已存在, name = Existing Item", exception.getMessage());
        }
    }

    @Test
    void removeDrawItem_shouldRemoveItemAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);

        // Mock the domain registry for award id generator
        try (MockedStatic<DrawDomainRegistry> mockedRegistry = mockStatic(DrawDomainRegistry.class)) {
            DrawItemIdGenerator idGenerator = mock(DrawItemIdGenerator.class);
            when(idGenerator.nextAwardId()).thenReturn(itemId);
            mockedRegistry.when(DrawDomainRegistry::awardIdGenerator).thenReturn(idGenerator);

            DrawItemInfo itemInfo = new DrawItemInfo("Item to Remove", "Description", DrawItemType.COUPON, 10);
            DrawItem drawItem = new DrawItem(
                    itemId,
                    itemInfo.name(),
                    itemInfo.description(),
                    itemInfo.type(),
                    itemInfo.weight()
            );
            HashSet<DrawItem> items = new HashSet<>();
            items.add(drawItem);
            drawPool.setDrawItems(items);


            // When
            drawPool.removeDrawItem(itemId);

            // Then
            assertEquals(0, drawPool.getDrawItems().size());
            assertEquals(1, drawPool.getEvents().size());
            assertInstanceOf(DrawItemRemovedEvent.class, drawPool.getEvents().getFirst());

            DrawItemRemovedEvent event = (DrawItemRemovedEvent) drawPool.getEvents().getFirst();
            assertEquals(DRAW_POOL_ID, event.getDrawPoolId().getId());
            assertEquals(itemId, event.getAwardId());
        }
    }

    @Test
    void removeDrawItem_shouldNotPublishEvent_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);

        // When
        drawPool.removeDrawItem(nonExistentItemId);

        // Then
        assertEquals(0, drawPool.getEvents().size());
    }

    @Test
    void create_shouldPublishDrawPoolCreatedEvent() {
        // When
        drawPool.create();

        // Then
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(DrawPoolCreatedEvent.class, drawPool.getEvents().getFirst());
    }

    @Test
    void delete_shouldPublishDrawPoolDeletedEvent() {
        // When
        drawPool.delete();

        // Then
        assertEquals(1, drawPool.getEvents().size());
        assertInstanceOf(DrawPoolDeletedEvent.class, drawPool.getEvents().getFirst());
    }
}