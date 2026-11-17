package com.inventory.ui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Console-based menu for interacting with inventory operations.
 *
 * @author Kilo
 */
public class ConsoleUI {

    private final InventoryService inventoryService;
    private final Scanner scanner;
    private final PrintStream output;

    /**
     * Creates the console interface with its inventory service dependency.
     *
     * @param inventoryService service used to perform inventory operations
     * @throws IllegalArgumentException if the service is null
     */
    public ConsoleUI(InventoryService inventoryService) {
        this(inventoryService, new Scanner(System.in), System.out);
    }

    /**
     * Creates the interface with injectable streams for automated testing.
     *
     * @param inventoryService service used to perform inventory operations
     * @param scanner source of user input; closed when {@link #start()} ends
     * @param output destination for prompts and results
     * @throws IllegalArgumentException if any dependency is null
     */
    public ConsoleUI(InventoryService inventoryService, Scanner scanner, PrintStream output) {
        if (inventoryService == null) {
            throw new IllegalArgumentException("InventoryService must not be null");
        }
        if (scanner == null || output == null) {
            throw new IllegalArgumentException("Scanner and output must not be null");
        }
        this.inventoryService = inventoryService;
        this.scanner = scanner;
        this.output = output;
    }

    /** Starts the menu loop and closes the input scanner when the session ends. */
    public void start() {
        try (scanner) {
            boolean running = true;
            while (running && scanner.hasNextLine()) {
                displayMenu();
                String choiceText = readLine("Enter your choice: ");
                if (choiceText == null) {
                    break;
                }
                Integer choice = parseInteger(choiceText, "Menu choice");
                if (choice == null) {
                    continue;
                }
                switch (choice) {
                    case 1 -> addItem();
                    case 2 -> viewAllItems();
                    case 3 -> searchItem();
                    case 4 -> updateItem();
                    case 5 -> deleteItem();
                    case 6 -> output.println("Total inventory records: " + inventoryService.getItemCount());
                    case 7 -> {
                        output.println("Thank you for using the Inventory Management System. Goodbye!");
                        running = false;
                    }
                    default -> output.println("Invalid menu choice. Please enter a number from 1 to 7.");
                }
                if (running) {
                    output.println();
                }
            }
        }
    }

    private void displayMenu() {
        output.println("========================================");
        output.println("       INVENTORY MANAGEMENT SYSTEM");
        output.println("========================================");
        output.println("1. Add New Item");
        output.println("2. View All Items");
        output.println("3. Search Item by ID");
        output.println("4. Update Item");
        output.println("5. Delete Item");
        output.println("6. View Total Inventory Count");
        output.println("7. Exit");
    }

    private void addItem() {
        String name = readLine("Enter item name: ");
        if (name == null) return;
        Integer quantity = readInteger("Enter item quantity: ", "Quantity");
        if (quantity == null) return;
        try {
            Item item = inventoryService.addItem(name, quantity);
            output.println("Item added successfully. Generated ID: " + item.getId());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            output.println("Unable to add item: " + exception.getMessage());
        }
    }

    private void viewAllItems() {
        List<Item> items = inventoryService.getAllItems();
        if (items.isEmpty()) {
            output.println("Inventory is empty.");
            return;
        }
        output.printf("%-8s %-28s %-12s %-18s%n", "ID", "NAME", "QUANTITY", "STOCK STATUS");
        output.println("---------------------------------------------------------------------");
        for (Item item : items) {
            output.printf("%-8d %-28s %-12d %-18s%n", item.getId(), item.getName(),
                    item.getQuantity(), item.getStockStatus().getLabel());
        }
    }

    private void searchItem() {
        Integer id = readInteger("Enter item ID: ", "Item ID");
        if (id == null) return;
        try {
            Optional<Item> item = inventoryService.getItemById(id);
            if (item.isPresent()) {
                printItem(item.get());
            } else {
                output.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            output.println("Unable to search item: " + exception.getMessage());
        }
    }

    private void updateItem() {
        Integer id = readInteger("Enter item ID to update: ", "Item ID");
        if (id == null) return;
        String name = readLine("Enter updated item name: ");
        if (name == null) return;
        Integer quantity = readInteger("Enter updated quantity: ", "Quantity");
        if (quantity == null) return;
        try {
            if (inventoryService.updateItem(id, name, quantity)) {
                output.println("Item updated successfully.");
            } else {
                output.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            output.println("Unable to update item: " + exception.getMessage());
        }
    }

    private void deleteItem() {
        Integer id = readInteger("Enter item ID to delete: ", "Item ID");
        if (id == null) return;
        try {
            if (inventoryService.deleteItem(id)) {
                output.println("Item deleted successfully.");
            } else {
                output.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            output.println("Unable to delete item: " + exception.getMessage());
        }
    }

    private Integer readInteger(String prompt, String fieldName) {
        String text = readLine(prompt);
        return text == null ? null : parseInteger(text, fieldName);
    }

    private Integer parseInteger(String text, String fieldName) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException exception) {
            output.println(fieldName + " must be a whole number. Please try again.");
            return null;
        }
    }

    private String readLine(String prompt) {
        output.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private void printItem(Item item) {
        output.printf("ID: %d | Name: %s | Quantity: %d | Stock status: %s%n",
                item.getId(), item.getName(), item.getQuantity(), item.getStockStatus().getLabel());
    }
}
