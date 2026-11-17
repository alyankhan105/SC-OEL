package com.inventory.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void shouldConstructItemAndReturnItsValues() {
        Item item = new Item(7, "  Notebook  ", 12);

        assertAll(
                () -> assertEquals(7, item.getId()),
                () -> assertEquals("Notebook", item.getName()),
                () -> assertEquals(12, item.getQuantity()));
    }

    @Test
    void shouldUpdateNameAndQuantityThroughSetters() {
        Item item = new Item(1, "Pencil", 2);

        item.setName(" Pen ");
        item.setQuantity(11);

        assertEquals("Pen", item.getName());
        assertEquals(11, item.getQuantity());
    }

    @Test
    void shouldRejectInvalidNamesAndNegativeQuantities() {
        Item item = new Item(1, "Pencil", 2);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> item.setName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> item.setName("  ")),
                () -> assertThrows(IllegalArgumentException.class, () -> item.setQuantity(-1)));
    }

    @Test
    void shouldDeriveStockStatusAtBoundaryQuantities() {
        assertAll(
                () -> assertEquals(StockStatus.OUT_OF_STOCK, new Item(1, "A", 0).getStockStatus()),
                () -> assertEquals(StockStatus.LOW_STOCK, new Item(2, "B", 1).getStockStatus()),
                () -> assertEquals(StockStatus.LOW_STOCK, new Item(3, "C", 10).getStockStatus()),
                () -> assertEquals(StockStatus.IN_STOCK, new Item(4, "D", 11).getStockStatus()));
    }
}
