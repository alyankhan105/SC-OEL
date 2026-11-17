# Inventory Management System

A Java-based Inventory Management System built as a university Open-Ended Lab (OEL) project.

## Overview
This system helps a business manage products, categories, stock quantities, inventory transactions, suppliers, sales/purchases, low-stock alerts, and inventory reports. It is implemented in Java using clean OOP principles and custom data structures, with no external frameworks or dependencies.

## Project Structure
```
inventory-management-system/
├── .gitignore
├── README.md
├── PROJECT_OVERVIEW.md
├── REQUIREMENTS.md
├── SYSTEM_ARCHITECTURE.md
├── DSA_DESIGN.md
├── src/
│   ├── main/java/inventory/
│   │   ├── model/          # Domain entities (Product, Category, Supplier, StockTransaction)
│   │   ├── service/        # Business logic (InventoryManager, ReportService, ...)
│   │   ├── repository/     # Data access abstractions
│   │   ├── data_structure/ # Custom DSA implementations
│   │   ├── utility/        # Cross-cutting helpers
│   │   ├── ui/             # Console user interface
│   │   └── exception/      # Custom exceptions
│   └── test/java/inventory/# Unit tests (future phases)
```

## Technology
- Language: Java 21 (LTS)
- Build: Standard Java project layout (no Maven/Gradle)
- Dependencies: None

## Development Phases
- Phase 0: Project foundation, design, documentation (complete)
- Phase 1: Core domain model classes
- Phase 2: Custom data structures and repository layer
- Phase 3: Inventory service and business operations
- Phase 4: Reporting, low-stock alerts, and console UI
- Phase 5: Testing and final report

## How to Compile and Run
```bash
# Compile all source files
javac -d out src/main/java/inventory/**/*.java

# Run the application
java -cp out inventory.Main
```

> Note: UI and business logic are implemented in later phases. Phase 0 contains project structure and documentation only.

## License
Educational use — University OEL project.
