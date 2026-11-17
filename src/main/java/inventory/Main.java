package inventory;

/**
 * Compatibility entry point for the original Phase 0 package layout.
 * Delegates to the current {@code com.inventory} application.
 */
public final class Main {

    private Main() {
        // utility-style class, not instantiable
    }

    public static void main(String[] args) {
        com.inventory.Main.main(args);
    }
}
