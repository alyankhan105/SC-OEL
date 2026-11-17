package com.inventory.service;

import com.inventory.model.Item;
import com.inventory.validation.InputValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages inventory items in memory and keeps the backing list encapsulated.
 * Names and quantities are validated by {@link Item}.
 *
 * @author Kilo
 */
public class InventoryService {

    private final ArrayList<Item> inventory;
    private int nextId;

    /**
     * Constructs an {@code InventoryService} with an empty inventory list.
     * The next automatically assigned item ID starts at 1.
     */
    public InventoryService() {
        this.inventory = new ArrayList<>();
        this.nextId = 1;
    }

    /**
     * Creates and stores an item with an automatically assigned unique ID.
     *
     * @param name item name; must not be null or blank
     * @param quantity initial quantity; must not be negative
     * @return the newly created item
     * @throws IllegalArgumentException if the name is null or blank or quantity is negative
     * @throws IllegalStateException if no further positive integer IDs are available
     */
    public Item addItem(String name, int quantity) {
        if (nextId <= 0) {
            throw new IllegalStateException("No further positive item IDs are available");
        }
        Item item = new Item(nextId, name, quantity);
        inventory.add(item);
        nextId = nextId == Integer.MAX_VALUE ? 0 : nextId + 1;
        return item;
    }

    /**
     * Returns an immutable snapshot of all items in insertion order.
     *
     * @return immutable snapshot; empty when the inventory has no items
     */
    public List<Item> getAllItems() {
        return List.copyOf(inventory);
    }

    /**
     * Finds an item by ID.
     *
     * @param id positive item ID
     * @return the matching item, or empty if no item has that ID
     * @throws IllegalArgumentException if the ID is not positive
     */
    public Optional<Item> getItemById(int id) {
        InputValidator.validateItemId(id);
        return inventory.stream().filter(item -> item.getId() == id).findFirst();
    }

    /**
     * Updates the name and quantity of an existing item without replacing it.
     *
     * @param id positive ID of the item to update
     * @param newName new item name; must not be null or blank
     * @param newQuantity new quantity; must not be negative
     * @return true if the item was found and updated, otherwise false
     * @throws IllegalArgumentException if the ID, name, or quantity is invalid
     */
    public boolean updateItem(int id, String newName, int newQuantity) {
        InputValidator.validateItemId(id);
        Optional<Item> item = getItemById(id);
        if (item.isEmpty()) {
            return false;
        }
        // Validate both values before mutating so a failed update changes nothing.
        Item candidate = new Item(id, newName, newQuantity);
        item.get().setName(candidate.getName());
        item.get().setQuantity(candidate.getQuantity());
        return true;
    }

    /**
     * Removes the item with the given ID.
     *
     * @param id positive ID of the item to remove
     * @return true if an item was removed, otherwise false
     * @throws IllegalArgumentException if the ID is not positive
     */
    public boolean deleteItem(int id) {
        InputValidator.validateItemId(id);
        return inventory.removeIf(item -> item.getId() == id);
    }

    /**
     * Returns the number of records currently in inventory.
     *
     * @return inventory record count
     */
    public int getItemCount() {
        return inventory.size();
    }

    /**
     * Returns a mutable defensive copy retained for compatibility with the
     * original Phase 1 API. Mutating the returned list does not change inventory.
     *
     * @return a new list containing the current item references
     */
    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    /**
     * Returns the number of items currently in inventory, retained for
     * compatibility with the original Phase 1 API.
     *
     * @return the inventory size
     */
    public int size() {
        return getItemCount();
    }
}
