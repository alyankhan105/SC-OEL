package com.inventory.model;

import com.inventory.validation.InputValidator;

/**
 * Represents a single product item held in inventory.
 * <p>
 * This class encapsulates the core attributes of an inventory item and exposes
 * them through getters and validated setters. The ID is immutable after
 * construction. Stock status is derived
 * dynamically from the current quantity via {@link StockStatus} rather than
 * stored as a separate field, ensuring the status always reflects the latest
 * quantity.
 *
 * @author Kilo
 */
public class Item {

    private final int id;
    private String name;
    private int quantity;

    /**
     * Constructs an {@code Item} with the given attributes.
     * The ID is validated by {@link InputValidator}; name and quantity are
     * validated by their respective setters.
     *
     * @param id       the unique identifier of the item (must be positive)
     * @param name     the display name of the item (must not be null or blank)
     * @param quantity the initial stock quantity (must not be negative)
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Item(int id, String name, int quantity) {
        InputValidator.validateItemId(id);
        this.id = id;
        setName(name);
        setQuantity(quantity);
    }

    /**
     * Returns the unique identifier of this item.
     *
     * @return the item id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the display name of this item.
     *
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of this item.
     *
     * @param name the new item name (must not be null or blank)
     * @throws IllegalArgumentException if the name is null or blank
     */
    public void setName(String name) {
        InputValidator.validateName(name);
        this.name = name.strip();
    }

    /**
     * Returns the current stock quantity of this item.
     *
     * @return the stock quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the stock quantity of this item.
     *
     * @param quantity the new stock quantity (must not be negative)
     * @throws IllegalArgumentException if the quantity is negative
     */
    public void setQuantity(int quantity) {
        InputValidator.validateQuantity(quantity);
        this.quantity = quantity;
    }

    /**
     * Returns the current stock status calculated dynamically from quantity.
     *
     * @return the {@link StockStatus} for the current quantity
     */
    public StockStatus getStockStatus() {
        return StockStatus.of(quantity);
    }

    /**
     * Returns a readable string representation of this item.
     *
     * @return a string describing the item's id, name, quantity, and status
     */
    @Override
    public String toString() {
        return "Item[id=" + id + ", name=" + name + ", quantity=" + quantity
                + ", status=" + getStockStatus() + "]";
    }
}
