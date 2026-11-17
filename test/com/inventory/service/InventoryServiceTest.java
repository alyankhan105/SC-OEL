package com.inventory.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.inventory.model.Item;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() {
        service = new InventoryService();
    }

    @Test
    void shouldAddValidAndMultipleItemsWithUniqueIds() {
        Item first = service.addItem("Keyboard", 20);
        Item second = service.addItem("Mouse", 3);

        assertAll(
                () -> assertEquals(1, first.getId()),
                () -> assertEquals(2, second.getId()),
                () -> assertNotSame(first, second),
                () -> assertEquals(2, service.getItemCount()));
    }

    @Test
    void shouldReturnEmptyListWhenInventoryIsEmptyAndSnapshotWhenPopulated() {
        assertTrue(service.getAllItems().isEmpty());
        Item item = service.addItem("Monitor", 5);
        List<Item> items = service.getAllItems();

        assertEquals(List.of(item), items);
        assertThrows(UnsupportedOperationException.class, items::clear);
        service.getInventory().clear();
        assertEquals(1, service.getItemCount());
    }

    @Test
    void shouldFindExistingItemAndReturnEmptyForMissingId() {
        Item item = service.addItem("Desk", 2);

        assertSame(item, service.getItemById(item.getId()).orElseThrow());
        assertTrue(service.getItemById(999).isEmpty());
    }

    @Test
    void shouldUpdateExistingObjectWithoutReplacingIt() {
        Item item = service.addItem("Old name", 15);

        assertTrue(service.updateItem(item.getId(), "New name", 4));

        assertSame(item, service.getItemById(item.getId()).orElseThrow());
        assertEquals("New name", item.getName());
        assertEquals(4, item.getQuantity());
    }

    @Test
    void shouldReturnFalseWhenUpdatingMissingItem() {
        assertFalse(service.updateItem(99, "Missing", 1));
    }

    @Test
    void shouldNotPartiallyModifyItemWhenUpdateInformationIsInvalid() {
        Item item = service.addItem("Original", 8);

        assertThrows(IllegalArgumentException.class,
                () -> service.updateItem(item.getId(), "Changed", -1));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateItem(item.getId(), "  ", 9));

        assertEquals("Original", item.getName());
        assertEquals(8, item.getQuantity());
    }

    @Test
    void shouldDeleteExistingItemAndReportMissingItem() {
        Item item = service.addItem("Delete me", 1);

        assertTrue(service.deleteItem(item.getId()));
        assertFalse(service.deleteItem(item.getId()));
        assertEquals(0, service.getItemCount());
    }

    @Test
    void shouldNotReuseIdsAfterDeletion() {
        Item first = service.addItem("First", 1);
        Item second = service.addItem("Second", 2);
        service.deleteItem(first.getId());

        Item third = service.addItem("Third", 3);

        assertEquals(3, third.getId());
        assertTrue(third.getId() != second.getId());
    }

    @Test
    void shouldRejectInvalidAddsAndIds() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.addItem(" ", 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> service.addItem("Valid", -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> service.getItemById(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteItem(-1)));
        assertEquals(0, service.getItemCount());
    }
}
