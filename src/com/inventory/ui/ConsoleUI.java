package com.inventory.ui;

import com.inventory.model.Item;
import com.inventory.service.InventoryService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Console-based menu for interacting with inventory operations.
 *
 * @author Kilo
 */
public class ConsoleUI {

    private final InventoryService inventoryService;

    /**
     * Creates the console interface with its inventory service dependency.
     *
     * @param inventoryService service used to perform inventory operations
     * @throws IllegalArgumentException if the service is null
     */
    public ConsoleUI(InventoryService inventoryService) {
        if (inventoryService == null) {
            throw new IllegalArgumentException("InventoryService must not be null");
        }
        this.inventoryService = inventoryService;
    }

    /** Starts the menu loop and closes the input scanner when the session ends. */
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running && scanner.hasNextLine()) {
                displayMenu();
                String choiceText = readLine(scanner, "Enter your choice: ");
                if (choiceText == null) {
                    break;
                }
                Integer choice = parseInteger(choiceText, "Menu choice");
                if (choice == null) {
                    continue;
                }
                switch (choice) {
                    case 1 -> addItem(scanner);
                    case 2 -> viewAllItems();
                    case 3 -> searchItem(scanner);
                    case 4 -> updateItem(scanner);
                    case 5 -> deleteItem(scanner);
                    case 6 -> System.out.println("Total inventory records: " + inventoryService.getItemCount());
                    case 7 -> {
                        System.out.println("Thank you for using the Inventory Management System. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid menu choice. Please enter a number from 1 to 7.");
                }
                if (running) {
                    System.out.println();
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("========================================");
        System.out.println("       INVENTORY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add New Item");
        System.out.println("2. View All Items");
        System.out.println("3. Search Item by ID");
        System.out.println("4. Update Item");
        System.out.println("5. Delete Item");
        System.out.println("6. View Total Inventory Count");
        System.out.println("7. Exit");
    }

    private void addItem(Scanner scanner) {
        String name = readLine(scanner, "Enter item name: ");
        if (name == null) return;
        Integer quantity = readInteger(scanner, "Enter item quantity: ", "Quantity");
        if (quantity == null) return;
        try {
            Item item = inventoryService.addItem(name, quantity);
            System.out.println("Item added successfully. Generated ID: " + item.getId());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Unable to add item: " + exception.getMessage());
        }
    }

    private void viewAllItems() {
        List<Item> items = inventoryService.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.printf("%-8s %-28s %-12s %-18s%n", "ID", "NAME", "QUANTITY", "STOCK STATUS");
        System.out.println("---------------------------------------------------------------------");
        for (Item item : items) {
            System.out.printf("%-8d %-28s %-12d %-18s%n", item.getId(), item.getName(),
                    item.getQuantity(), item.getStockStatus().getLabel());
        }
    }

    private void searchItem(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter item ID: ", "Item ID");
        if (id == null) return;
        try {
            Optional<Item> item = inventoryService.getItemById(id);
            if (item.isPresent()) {
                printItem(item.get());
            } else {
                System.out.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Unable to search item: " + exception.getMessage());
        }
    }

    private void updateItem(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter item ID to update: ", "Item ID");
        if (id == null) return;
        String name = readLine(scanner, "Enter updated item name: ");
        if (name == null) return;
        Integer quantity = readInteger(scanner, "Enter updated quantity: ", "Quantity");
        if (quantity == null) return;
        try {
            if (inventoryService.updateItem(id, name, quantity)) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Unable to update item: " + exception.getMessage());
        }
    }

    private void deleteItem(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter item ID to delete: ", "Item ID");
        if (id == null) return;
        try {
            if (inventoryService.deleteItem(id)) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with ID " + id + ".");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Unable to delete item: " + exception.getMessage());
        }
    }

    private static Integer readInteger(Scanner scanner, String prompt, String fieldName) {
        String text = readLine(scanner, prompt);
        return text == null ? null : parseInteger(text, fieldName);
    }

    private static Integer parseInteger(String text, String fieldName) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException exception) {
            System.out.println(fieldName + " must be a whole number. Please try again.");
            return null;
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static void printItem(Item item) {
        System.out.printf("ID: %d | Name: %s | Quantity: %d | Stock status: %s%n",
                item.getId(), item.getName(), item.getQuantity(), item.getStockStatus().getLabel());
    }
}
