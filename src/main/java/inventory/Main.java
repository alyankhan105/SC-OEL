package inventory;

/** Compatibility entry point for the original Phase 0 package layout. */
public final class Main {

    private Main() {
        // utility-style class, not instantiable
    }

    /**
     * Delegates application startup to the current entry point.
     *
     * @param args command-line arguments passed to the current entry point
     */
    public static void main(String[] args) {
        com.inventory.Main.main(args);
    }
}
