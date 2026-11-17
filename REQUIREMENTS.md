# Inventory Management System — Requirements

## 1. Intended Users
| User | Role |
|------|------|
| Inventory Officer | Adds/edits products, records purchases and sales, adjusts stock |
| Shop Manager | Views inventory reports, reviews low-stock alerts, manages suppliers |

## 2. Functional Requirements

### FR1 — Product Management
- Add a new product with name, category, supplier, price, and stock quantity.
- View details of an existing product by its product ID.
- List all products in the inventory.
- Update product details (name, price, category, supplier).
- Remove a product from inventory (when no longer stocked).

### FR2 — Category Management
- Add a product category (name, description).
- List all categories.
- View all products belonging to a category.

### FR3 — Supplier Management
- Add a supplier (name, contact, address).
- List all suppliers.
- View products supplied by a given supplier.

### FR4 — Stock Transactions
- Record a purchase transaction (increases stock).
- Record a sale transaction (decreases stock).
- Record a stock adjustment (manual correction).
- View transaction history for a product.
- View all transactions within a date range.

### FR5 — Stock Quantities
- Query current stock quantity of a product.
- Prevent stock from going negative on a sale/adjustment.
- Track total inventory value (sum of quantity × price).

### FR6 — Low-Stock Alerts
- Define a low-stock threshold per product (or global default).
- List all products whose stock is at or below the threshold.
- Flag products needing restocking.

### FR7 — Inventory Reports
- Generate a stock-level report (all products with quantities).
- Generate a low-stock report.
- Generate a sales/purchase summary report.
- Generate a category-wise stock report.

## 3. Non-Functional Requirements

| Category | Requirement |
|----------|-------------|
| Performance | Product lookup by ID in near-constant time; transaction log append in O(1) |
| Maintainability | Modular packages; each class has a single responsibility; clean naming |
| Reliability | Invalid operations (negative stock, missing ID) throw clear exceptions |
| Usability | Console UI with simple numbered menus and clear input prompts |
| Portability | Runs on any machine with Java 21 JRE; no external dependencies |
| Testability | Business logic separated from UI so it can be unit-tested independently |

## 4. Main System Operations
1. Add / view / update / remove product
2. Add / view category and supplier
3. Record purchase / sale / adjustment transaction
4. Check stock quantity and total inventory value
5. List low-stock products
6. Generate inventory reports
7. View transaction history

## 5. Scope of Initial Version (Phase 0–4 target)
- In-memory operation only (no database).
- Console-based user interface.
- Single user, single session.
- Core entities: Product, Category, Supplier, StockTransaction, Inventory.
- Custom data structures (not over-reliant on `java.util.Collections` where a custom structure better demonstrates DSA).
- Low-stock alerts and basic reports.

## 6. Explicitly Out of Scope (Initial Version)
- GUI / desktop / web interface
- Database or file persistence
- Login, roles, permissions
- Barcode / RFID integration
- Multi-threading / concurrent users
- Import/export (CSV/Excel)
