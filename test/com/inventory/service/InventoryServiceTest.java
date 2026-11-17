package com.inventory.service;

import com.inventory.model.Item;
import com.inventory.model.StockStatus;
import com.inventory.validation.InputValidator;
import java.util.List;

/** Lightweight executable checks for Phase 2, requiring no external test library. */
public final class InventoryServiceTest {

    private InventoryServiceTest() { }

    public static void main(String[] args) {
        testInputValidation();
        InventoryService service = new InventoryService();
        Item first = service.addItem("Keyboard", 20);
        Item second = service.addItem("Mouse", 3);
        check(first.getId() == 1 && second.getId() == 2, "automatic IDs");
        check(service.getItemCount() == 2, "item count");
        check(service.getAllItems().size() == 2, "all items");
        check(service.getItemById(first.getId()).orElseThrow() == first, "lookup existing item");
        check(service.getItemById(99).isEmpty(), "lookup missing item");
        expectIllegalArgument(() -> service.addItem("  ", 1), "blank name rejected");
        expectIllegalArgument(() -> service.addItem(null, 1), "null name rejected");
        expectIllegalArgument(() -> service.addItem("Bad quantity", -1), "negative quantity rejected");
        check(service.updateItem(first.getId(), "Updated keyboard", 5), "update existing item");
        check(service.getItemById(first.getId()).orElseThrow() == first, "update preserves object");
        check(first.getName().equals("Updated keyboard"), "updated name");
        check(first.getStockStatus() == StockStatus.LOW_STOCK, "stock status updates dynamically");
        check(!service.updateItem(99, "Missing", 1), "update missing item");
        expectIllegalArgument(() -> service.updateItem(first.getId(), " ", 1), "invalid update rejected");
        check(first.getName().equals("Updated keyboard"), "failed update leaves item unchanged");
        check(service.deleteItem(second.getId()), "delete existing item");
        check(!service.deleteItem(second.getId()), "delete missing item");
        Item third = service.addItem("Monitor", 0);
        check(third.getId() == 3 && third.getId() != second.getId(), "deleted ID is not reused");
        check(third.getStockStatus() == StockStatus.OUT_OF_STOCK, "zero quantity status");
        List<Item> snapshot = service.getAllItems();
        try {
            snapshot.clear();
            throw new AssertionError("getAllItems result should be immutable");
        } catch (UnsupportedOperationException expected) {
            check(service.getItemCount() == 2, "immutable snapshot keeps inventory intact");
        }
        service.getInventory().clear();
        check(service.getItemCount() == 2, "legacy defensive copy");
        expectIllegalArgument(() -> service.getItemById(0), "invalid ID rejected");
        expectIllegalArgument(() -> service.deleteItem(-1), "negative ID rejected");
        expectIllegalArgument(() -> new Item(0, "Invalid ID", 1), "item constructor rejects invalid ID");
        expectIllegalArgument(() -> first.setName("\t "), "item setter rejects blank name");
        expectIllegalArgument(() -> first.setQuantity(-1), "item setter rejects negative quantity");
        check(first.getName().equals("Updated keyboard") && first.getQuantity() == 5,
                "rejected setter input leaves item unchanged");
        System.out.println("All InventoryService checks passed.");
    }

    private static void testInputValidation() {
        expectIllegalArgument(() -> InputValidator.validateName(null), "null name rejected");
        expectIllegalArgument(() -> InputValidator.validateName(""), "empty name rejected");
        expectIllegalArgument(() -> InputValidator.validateName(" \t\n"), "whitespace name rejected");
        InputValidator.validateName(" Valid ");
        check(new Item(1, "  Padded item  ", 0).getName().equals("Padded item"),
                "valid names are trimmed");
        expectIllegalArgument(() -> InputValidator.validateQuantity(-1), "negative quantity rejected");
        InputValidator.validateQuantity(0);
        InputValidator.validateQuantity(42);
        expectIllegalArgument(() -> InputValidator.validateItemId(-1), "negative ID rejected");
        expectIllegalArgument(() -> InputValidator.validateItemId(0), "zero ID rejected");
        InputValidator.validateItemId(1);
    }

    private static void expectIllegalArgument(Runnable action, String description) {
        try {
            action.run();
            throw new AssertionError(description);
        } catch (IllegalArgumentException expected) {
            // Expected validation result.
        }
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
    }
}
