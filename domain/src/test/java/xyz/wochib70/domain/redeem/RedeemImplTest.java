package xyz.wochib70.domain.redeem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import xyz.wochib70.domain.AggregateTestBase;
import xyz.wochib70.domain.DefaultIdentifierId;
import xyz.wochib70.domain.IdentifierId;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.redeem.events.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedeemImplTest extends AggregateTestBase {

    private RedeemImpl redeem;
    private static final Long REDEEM_ID = 1L;
    private static final Long ACTIVITY_ID = 2L;

    @BeforeEach
    void setUp() {
        redeem = new RedeemImpl(new DefaultIdentifierId<>(REDEEM_ID));
        redeem.setActivityId(new DefaultIdentifierId<>(ACTIVITY_ID));
        redeem.setName("Test Redeem");
        redeem.setRedeemItems(new HashSet<>());
    }

    @Test
    void getRedeemId_shouldReturnCorrectId() {
        // When
        IdentifierId<Long> redeemId = redeem.getRedeemId();

        // Then
        assertEquals(REDEEM_ID, redeemId.getId());
    }

    @Test
    void getActivityId_shouldReturnCorrectId() {
        // When
        IdentifierId<Long> activityId = redeem.getActivityId();

        // Then
        assertEquals(ACTIVITY_ID, activityId.getId());
    }

    @Test
    void modifyRedeemName_shouldUpdateNameAndPublishEvent_whenNameIsDifferent() {
        // Given
        String newName = "New Redeem Name";

        // When
        redeem.modifyRedeemName(newName);

        // Then
        assertEquals(newName, redeem.getName());
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemNameModifiedEvent.class, redeem.getEvents().getFirst());
        
        RedeemNameModifiedEvent event = (RedeemNameModifiedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(newName, event.getName());
    }

    @Test
    void modifyRedeemName_shouldNotPublishEvent_whenNameIsSame() {
        // Given
        String sameName = redeem.getName();

        // When
        redeem.modifyRedeemName(sameName);

        // Then
        assertEquals(0, redeem.getEvents().size());
    }

    @Test
    void getRedeemItemPriceOrThrow_shouldReturnPrice_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        RedeemItemPrice price = new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100);
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Test Item", "Description");
        item.setItemPrice(price);
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);

        // When
        RedeemItemPrice result = redeem.getRedeemItemPriceOrThrow(itemId);

        // Then
        assertEquals(price, result);
    }

    @Test
    void getRedeemItemPriceOrThrow_shouldThrowException_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);

        // When & Then
        NoSuchRedeemItemException exception = assertThrows(NoSuchRedeemItemException.class, () -> {
            redeem.getRedeemItemPriceOrThrow(nonExistentItemId);
        });
        assertEquals("兑换项不存在", exception.getMessage());
    }

    @Test
    void redeem_shouldRedeemItemAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        UserId userId = new UserId(1L);
        int count = 2;
        
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Test Item", "Description");
        item.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);


        // When
        redeem.redeem(itemId, count, userId);

        // Then
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemItemRedeemedEvent.class, redeem.getEvents().getFirst());
        
        RedeemItemRedeemedEvent event = (RedeemItemRedeemedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(itemId, event.getRedeemItemId());
        assertEquals(count, event.getQuantity());
        assertEquals(userId, event.getUserId());
    }

    @Test
    void redeem_shouldThrowException_whenCountIsZero() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        UserId userId = new UserId(1L);
        int count = 0;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeem.redeem(itemId, count, userId);
        });
        assertEquals("兑换数量不能小于0", exception.getMessage());
    }

    @Test
    void redeem_shouldThrowException_whenCountIsNegative() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        UserId userId = new UserId(1L);
        int count = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            redeem.redeem(itemId, count, userId);
        });
        assertEquals("兑换数量不能小于0", exception.getMessage());
    }

    @Test
    void redeem_shouldThrowException_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);
        UserId userId = new UserId(1L);
        int count = 1;

        // When & Then
        NoSuchRedeemItemException exception = assertThrows(NoSuchRedeemItemException.class, () -> {
            redeem.redeem(nonExistentItemId, count, userId);
        });
        assertEquals("兑换项不存在", exception.getMessage());
    }

    @Test
    void addRedeemItem_shouldAddItemAndPublishEvent_whenItemNameDoesNotExist() {
        // Given
        String itemName = "New Item";
        RedeemItemInfo itemInfo = new RedeemItemInfo(
                itemName,
                "Description",
                RedeemItemType.COUPON,
                new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100)
        );
        
        // Mock the domain registry for redeem item id generator
        try (MockedStatic<RedeemDomainRegistry> mockedRegistry = mockStatic(RedeemDomainRegistry.class)) {
            RedeemItemIdGenerator idGenerator = mock(RedeemItemIdGenerator.class);
            when(idGenerator.nextRedeemItemId()).thenReturn(new DefaultIdentifierId<>(1L));
            mockedRegistry.when(RedeemDomainRegistry::redeemItemIdGenerator).thenReturn(idGenerator);

            // When
            redeem.addRedeemItem(itemInfo);

            // Then
            assertEquals(1, redeem.getRedeemItems().size());
            assertEquals(1, redeem.getEvents().size());
            assertInstanceOf(RedeemItemAddedEvent.class, redeem.getEvents().getFirst());
        }
    }

    @Test
    void addRedeemItem_shouldThrowException_whenItemNameAlreadyExists() {
        // Given
        String existingItemName = "Existing Item";
        RedeemItem existingItem = new RedeemItem();
        existingItem.setId(new DefaultIdentifierId<>(1L));
        existingItem.setItemInfo(existingItemName, "Description");
        existingItem.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        existingItem.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(existingItem);
        
        RedeemItemInfo newItemInfo = new RedeemItemInfo(
                existingItemName,
                "New Description",
                RedeemItemType.VIP,
                new RedeemItemPrice(new DefaultIdentifierId<>(2L), 200)
        );

        // When & Then
        DuplicatedRedeemItemNameException exception = assertThrows(DuplicatedRedeemItemNameException.class, () -> {
            redeem.addRedeemItem(newItemInfo);
        });
        assertEquals("兑换项名称重复", exception.getMessage());
    }

    @Test
    void removeRedeemItem_shouldRemoveItemAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Test Item", "Description");
        item.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);

        // When
        redeem.removeRedeemItem(itemId);

        // Then
        assertEquals(0, redeem.getRedeemItems().size());
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemItemRemovedEvent.class, redeem.getEvents().getFirst());
        
        RedeemItemRemovedEvent event = (RedeemItemRemovedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(itemId, event.getRedeemItemId());
    }

    @Test
    void removeRedeemItem_shouldNotPublishEvent_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);

        // When
        redeem.removeRedeemItem(nonExistentItemId);

        // Then
        assertEquals(0, redeem.getEvents().size());
    }

    @Test
    void modifyRedeemItemBasicInfo_shouldUpdateInfoAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        String newName = "New Name";
        String newDescription = "New Description";
        
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Old Name", "Old Description");
        item.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);

        // When
        redeem.modifyRedeemItemBasicInfo(itemId, newName, newDescription);

        // Then
        assertEquals(newName, item.getName());
        assertEquals(newDescription, item.getDescription());
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemItemInfoModifiedEvent.class, redeem.getEvents().getFirst());
        
        RedeemItemInfoModifiedEvent event = (RedeemItemInfoModifiedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(itemId, event.getRedeemItemId());
        assertEquals(newName, event.getName());
        assertEquals(newDescription, event.getDescription());
    }

    @Test
    void modifyRedeemItemBasicInfo_shouldThrowException_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);
        String newName = "New Name";
        String newDescription = "New Description";

        // When & Then
        NoSuchRedeemItemException exception = assertThrows(NoSuchRedeemItemException.class, () -> {
            redeem.modifyRedeemItemBasicInfo(nonExistentItemId, newName, newDescription);
        });
        assertEquals("兑换项不存在", exception.getMessage());
    }


    @Test
    void modifyRedeemItemPrice_shouldUpdatePriceAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        RedeemItemPrice newPrice = new RedeemItemPrice(new DefaultIdentifierId<>(2L), 200);
        
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Test Item", "Description");
        item.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);

        // When
        redeem.modifyRedeemItemPrice(itemId, newPrice);

        // Then
        assertEquals(newPrice, item.getPrice());
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemItemPriceModifiedEvent.class, redeem.getEvents().getFirst());
        
        RedeemItemPriceModifiedEvent event = (RedeemItemPriceModifiedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(itemId, event.getRedeemItemId());
        assertEquals(newPrice, event.getPrice());
    }

    @Test
    void modifyRedeemItemPrice_shouldThrowException_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);
        RedeemItemPrice newPrice = new RedeemItemPrice(new DefaultIdentifierId<>(2L), 200);

        // When & Then
        NoSuchRedeemItemException exception = assertThrows(NoSuchRedeemItemException.class, () -> {
            redeem.modifyRedeemItemPrice(nonExistentItemId, newPrice);
        });
        assertEquals("兑换项不存在", exception.getMessage());
    }

    @Test
    void modifyRedeemItemType_shouldUpdateTypeAndPublishEvent_whenItemExists() {
        // Given
        IdentifierId<Long> itemId = new DefaultIdentifierId<>(1L);
        RedeemItemType newType = RedeemItemType.VIP;
        
        RedeemItem item = new RedeemItem();
        item.setId(itemId);
        item.setItemInfo("Test Item", "Description");
        item.setItemPrice(new RedeemItemPrice(new DefaultIdentifierId<>(1L), 100));
        item.setItemType(RedeemItemType.COUPON);
        
        redeem.getRedeemItems().add(item);

        // When
        redeem.modifyRedeemItemType(itemId, newType);

        // Then
        assertEquals(newType, item.getType());
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemItemTypeModifiedEvent.class, redeem.getEvents().getFirst());
        
        RedeemItemTypeModifiedEvent event = (RedeemItemTypeModifiedEvent) redeem.getEvents().getFirst();
        assertEquals(REDEEM_ID, event.getRedeemId().getId());
        assertEquals(itemId, event.getRedeemItemId());
        assertEquals(newType, event.getType());
    }

    @Test
    void modifyRedeemItemType_shouldThrowException_whenItemDoesNotExist() {
        // Given
        IdentifierId<Long> nonExistentItemId = new DefaultIdentifierId<>(999L);
        RedeemItemType newType = RedeemItemType.VIP;

        // When & Then
        NoSuchRedeemItemException exception = assertThrows(NoSuchRedeemItemException.class, () -> {
            redeem.modifyRedeemItemType(nonExistentItemId, newType);
        });
        assertEquals("兑换项不存在", exception.getMessage());
    }

    @Test
    void create_shouldPublishRedeemPoolCreatedEvent() {
        // When
        redeem.create();

        // Then
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemPoolCreatedEvent.class, redeem.getEvents().getFirst());
    }

    @Test
    void delete_shouldPublishRedeemPoolDeletedEvent() {
        // When
        redeem.delete();

        // Then
        assertEquals(1, redeem.getEvents().size());
        assertInstanceOf(RedeemPoolDeletedEvent.class, redeem.getEvents().getFirst());
    }
}