# Inventory Management System — Project Overview

## 1. Project Title
Inventory Management System (IMS)

## 2. Course Context
University Open-Ended Lab (OEL) — Java Programming / Data Structures & Algorithms

## 3. Purpose
Build a console-based Java application that helps a business manage its inventory. The project demonstrates Object-Oriented Programming (OOP), Data Structures and Algorithms (DSA), and clean, modular Java design without relying on frameworks or external dependencies.

## 4. Business Domain
The system tracks products organized into categories, records stock quantities, logs every inventory transaction (purchase/sale/adjustment), manages suppliers, raises low-stock alerts, and produces inventory reports.

## 5. Key Entities
- Product — an item kept in stock
- Category — a grouping of related products
- Supplier — an external vendor that supplies products
- StockTransaction — a record of stock movement (purchase, sale, adjustment)
- Inventory — the aggregate collection of products and stock levels
- InventoryManager — orchestrates inventory operations

## 6. Intended Users
- Store clerk / inventory officer (day-to-day stock operations)
- Shop owner / manager (reports, low-stock review)

## 7. Out of Scope for Initial Version
- Graphical User Interface (GUI)
- Database persistence (data kept in memory)
- User authentication / roles
- Multi-user concurrent access
- Barcode scanning / integration
- E-commerce checkout

## 8. Technology Stack
- Language: Java 21 (LTS)
- Build: standard Java project layout (no Maven/Gradle — Maven is not available in this environment)
- Testing: plain Java / JUnit-style unit tests introduced in later phases
- No external libraries or frameworks

## 9. Deliverables (Phase 0)
- Requirements documentation
- Architecture documentation
- DSA selection and complexity analysis
- UML class diagram
- Initial project structure
- Git initialization and initial commit
- Testing strategy
- Phase 0 completion report

## 10. Phase Roadmap
- Phase 0: Project foundation, design, documentation (this phase)
- Phase 1: Core domain model classes (Product, Category, Supplier, StockTransaction)
- Phase 2: Custom data structures and repository layer
- Phase 3: Inventory service and business operations
- Phase 4: Reporting, low-stock alerts, and console UI
- Phase 5: Testing, bug fixes, and final report
