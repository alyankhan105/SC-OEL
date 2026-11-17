# Inventory Management System

A Java-based Inventory Management System built as a university Open-Ended Lab (OEL) project.

## Overview
This system helps a business manage products, categories, stock quantities, inventory transactions, suppliers, sales/purchases, low-stock alerts, and inventory reports. It is implemented in Java using clean OOP principles and custom data structures, with no external frameworks or dependencies.

## Project Structure
```
inventory-management-system/
├── pom.xml                         # Maven build and JUnit 5 test configuration
├── src/com/inventory/
│   ├── Main.java
│   ├── model/                       # Item and stock status
│   ├── service/                     # Inventory operations
│   ├── ui/                          # Console interface
│   └── validation/                  # Reusable validation
├── src/main/java/inventory/Main.java # Compatibility entry point
├── test/com/inventory/              # JUnit 5 tests
└── docs/testing-report.md           # Phase 5 test results
```

## Technology
- Language: Java 21 (LTS)
- Build: Maven
- Test framework: JUnit Jupiter 5

## Development Phases
- Phase 0: Project foundation, design, documentation (complete)
- Phase 1: Core domain model classes
- Phase 2: Custom data structures and repository layer
- Phase 3: Inventory service and business operations
- Phase 4: Reporting, low-stock alerts, and console UI
- Phase 5: Testing and final report

## How to Build, Test, and Run
```bash
# Run the automated test suite
mvn test

# Compile/package the application
mvn package

# Start the console application
java -cp target/classes com.inventory.Main
```

See [docs/testing-report.md](docs/testing-report.md) for the test scenarios and verified results.

## License
Educational use — University OEL project.
