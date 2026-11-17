package com.inventory.validation;

/**
 * Shared validation rules for inventory item data.
 * Validation methods throw {@link IllegalArgumentException} when input is invalid,
 * allowing callers to report the precise problem without boolean-result ambiguity.
 */
public final class InputValidator {

    private InputValidator() {
        // Utility class; do not instantiate.
    }

    /**
     * Validates an item name.
     *
     * @param name name to validate
     * @throws IllegalArgumentException if the name is null, empty, or whitespace-only
     */
    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name must not be null, empty, or blank");
        }
    }

    /**
     * Validates an item quantity; zero is a valid out-of-stock quantity.
     *
     * @param quantity quantity to validate
     * @throws IllegalArgumentException if the quantity is negative
     */
    public static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must not be negative: " + quantity);
        }
    }

    /**
     * Validates an item ID.
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if the ID is zero or negative
     */
    public static void validateItemId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Item ID must be positive: " + id);
        }
    }
}
