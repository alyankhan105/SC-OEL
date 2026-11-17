package com.inventory.service;

import com.inventory.model.Item;
import java.util.ArrayList;

/**
 * Service responsible for managing the in-memory collection of inventory items.
 * <p>
 * This class owns the inventory list and keeps it fully encapsulated. It does
 * not expose the internal {@code ArrayList} directly; callers receive a
 * defensive copy. No create/read/update/delete operations are implemented
 * here; those are reserved for a later phase.
 *
 * @author Kilo
 */
public class InventoryService {

    private final ArrayList<Item> inventory;

    /**
     * Constructs an {@code InventoryService} with an empty inventory list.
     */
    public InventoryService() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Returns a defensive copy of the current inventory.
     * <p>
     * Returning a copy preserves encapsulation: callers cannot mutate the
     * internal list through the returned reference.
     *
     * @return a new {@code ArrayList} containing the same items
     */
    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    /**
     * Returns the number of items currently in inventory.
     *
     * @return the inventory size
     */
    public int size() {
        return inventory.size();
    }
}
