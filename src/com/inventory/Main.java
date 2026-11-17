package com.inventory;

import com.inventory.service.InventoryService;
import com.inventory.ui.ConsoleUI;

/** Application entry point for the inventory management system.
 *
 * @author Kilo
 */
public final class Main {

    private Main() {
        // Utility-style entry point; do not instantiate.
    }

    /**
     * Creates the in-memory service and starts the console interface.
     *
     * @param args command-line arguments (currently unused)
     */
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        ConsoleUI consoleUI = new ConsoleUI(inventoryService);
        consoleUI.start();
    }
}
