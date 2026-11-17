package com.inventory.model;

/**
 * Enumeration of possible stock statuses for an inventory item.
 * <p>
 * The status is derived from the item quantity using configurable thresholds:
 * <ul>
 *   <li>quantity &gt; {@link #LOW_STOCK_THRESHOLD}: {@link #IN_STOCK}</li>
 *   <li>1 &le; quantity &le; {@link #LOW_STOCK_THRESHOLD}: {@link #LOW_STOCK}</li>
 *   <li>quantity == 0: {@link #OUT_OF_STOCK}</li>
 * </ul>
 * The low-stock threshold is kept as a constant so it can be adjusted in one
 * place without touching the calculation logic.
 *
 * @author Kilo
 */
public enum StockStatus {

    IN_STOCK("In Stock"),
    LOW_STOCK("Low Stock"),
    OUT_OF_STOCK("Out of Stock");

    /** Quantity at or below this value (excluding zero) is considered low stock. */
    public static final int LOW_STOCK_THRESHOLD = 10;

    private final String label;

    StockStatus(String label) {
        this.label = label;
    }

    /**
     * Returns the human-readable label for this status.
     *
     * @return the display label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Calculates the stock status dynamically from a given quantity.
     * The status is not stored; it is computed each time from the quantity.
     *
     * @param quantity the current stock quantity
     * @return the matching {@link StockStatus}
     */
    public static StockStatus of(int quantity) {
        if (quantity <= 0) {
            return OUT_OF_STOCK;
        }
        if (quantity <= LOW_STOCK_THRESHOLD) {
            return LOW_STOCK;
        }
        return IN_STOCK;
    }
}
