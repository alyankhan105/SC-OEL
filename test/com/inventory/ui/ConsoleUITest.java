package com.inventory.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.inventory.service.InventoryService;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleUITest {

    private InventoryService service;

    @BeforeEach
    void setUp() {
        service = new InventoryService();
    }

    @Test
    void shouldDisplayEmptyInventoryAndExitGracefully() {
        String output = runConsole("2\n7\n");

        assertTrue(output.contains("Inventory is empty."));
        assertTrue(output.contains("Goodbye!"));
    }

    @Test
    void shouldHandleValidAndInvalidMenuChoices() {
        String output = runConsole("nope\n9\n6\n7\n");

        assertTrue(output.contains("Menu choice must be a whole number."));
        assertTrue(output.contains("Invalid menu choice."));
        assertTrue(output.contains("Total inventory records: 0"));
    }

    @Test
    void shouldAddAndSearchForItemThroughMenu() {
        String output = runConsole("1\nWidget\n4\n3\n1\n7\n");

        assertTrue(output.contains("Item added successfully. Generated ID: 1"));
        assertTrue(output.contains("ID: 1 | Name: Widget | Quantity: 4"));
        assertEquals(1, service.getItemCount());
    }

    @Test
    void shouldReportNonNumericAndNegativeQuantityWithoutAddingItem() {
        String output = runConsole("1\nInvalid\nabc\n1\nAlso invalid\n-2\n7\n");

        assertTrue(output.contains("Quantity must be a whole number."));
        assertTrue(output.contains("Unable to add item: Quantity must not be negative: -2"));
        assertEquals(0, service.getItemCount());
    }

    private String runConsole(String input) {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(captured, true, StandardCharsets.UTF_8);
        ConsoleUI ui = new ConsoleUI(service, new Scanner(input), output);
        ui.start();
        output.flush();
        return captured.toString(StandardCharsets.UTF_8);
    }
}
