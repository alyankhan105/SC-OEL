# Inventory Management System — DSA Design

This document selects the data structures and algorithms used in the Inventory Management System and explains the trade-offs. Implementations are deferred to later phases; Phase 0 only selects and justifies them.

## 1. Selected Data Structures

### 1.1 HashMap (custom or java.util.HashMap) — Product, Category, Supplier storage by ID
- **Why needed:** Fast lookup, insert, and delete of an entity by its unique ID.
- **Where used:** `ProductRepository`, `CategoryRepository`, `SupplierRepository` — key = product/category/supplier ID, value = entity object.
- **Time complexity:** average O(1) for get/put/remove; worst-case O(n) with many hash collisions.
- **Why over alternatives:**
  - vs `ArrayList`: lookup by ID is O(n) with a linear scan — too slow as inventory grows.
  - vs `HashSet`: a Set stores only values, not key→value mappings, so it cannot retrieve an entity by ID.
  - vs sorted `TreeMap`: O(log n) is acceptable but unnecessary; we do not need ordered iteration by ID.

### 1.2 ArrayList / custom dynamic array — ordered product and transaction lists
- **Why needed:** Maintain insertion order and allow indexed access and iteration.
- **Where used:** product listings, transaction history, category product lists, report generation.
- **Time complexity:** get by index O(1); add at end amortized O(1); remove/search O(n).
- **Why over alternatives:**
  - vs `LinkedList`: indexed access is O(1) for ArrayList vs O(n) for LinkedList; reports and listings frequently index into the list.
  - vs `HashSet`: we need duplicates allowed (e.g. multiple transactions) and insertion order, which a Set does not guarantee.
  - vs array: ArrayList grows dynamically; fixed-size arrays would require manual resizing logic.

### 1.3 HashSet — unique category names / supplier IDs / low-stock product tracking
- **Why needed:** Fast membership testing and automatic deduplication.
- **Where used:** ensuring unique category names, tracking distinct low-stock product IDs without duplicates.
- **Time complexity:** add/contains/remove average O(1).
- **Why over alternatives:** `ArrayList.contains` is O(n); for deduplication and membership checks HashSet is far more efficient.

### 1.4 Queue (FIFO) — transaction processing / audit log
- **Why needed:** Process transactions in the order they were recorded; first-in-first-out semantics.
- **Where used:** pending transaction queue, recent-activity log.
- **Time complexity:** offer/poll O(1); peek O(1).
- **Why over alternatives:** `Stack` is LIFO (wrong order for an audit log); `ArrayList` removal from front is O(n) due to shifting.

### 1.5 Stack (LIFO) — undo last transaction / navigation history
- **Why needed:** Revert the most recent operation or support UI back-navigation.
- **Where used:** "undo last stock adjustment" feature, console menu back-stack.
- **Time complexity:** push/pop/peek O(1).
- **Why over alternatives:** A Queue would undo the oldest transaction, which is incorrect for an undo feature.

## 2. Selected Algorithms

### 2.1 Linear Search — find product by name or category
- **Why needed:** Users search by product name (not unique) which cannot use a HashMap keyed on ID.
- **Where used:** `ProductRepository.findByName`, category product listing.
- **Time complexity:** O(n).
- **Why over alternatives:** HashMap cannot help because name is not unique; sorting first then binary searching adds O(n log n) overhead for a one-off search.

### 2.2 Binary Search — find in a sorted ID list
- **Why needed:** Fast lookup when the ID list is kept sorted.
- **Where used:** optional optimized ID lookup in `data_structure` layer.
- **Time complexity:** O(log n) after O(n log n) sort (or O(n) to keep sorted on insert).
- **Why over alternatives:** HashMap gives O(1) and is preferred for ID lookup; binary search is included to demonstrate DSA and for cases where sorted order is already maintained.

### 2.3 Sorting (Merge Sort / Quick Sort) — reports ordered by value, name, or date
- **Why needed:** Reports must display products sorted by stock value, name, or transaction date.
- **Where used:** `ReportService` — sort products by total value, transactions by date.
- **Time complexity:** O(n log n) average/worst (Merge Sort); O(n log n) average, O(n²) worst (Quick Sort).
- **Why over alternatives:** Merge Sort is stable and O(n log n) worst-case — good for report sorting where stability matters. Quick Sort is in-place and typically faster on average but not stable and has O(n²) worst case. We prefer Merge Sort for reports.

### 2.4 Selection / Insertion Sort — small lists (demonstration)
- **Why needed:** Demonstrates simple O(n²) sorts for tiny datasets (e.g. fewer than 10 low-stock items).
- **Where used:** optional sorting of small alert lists in `data_structure`.
- **Time complexity:** O(n²).
- **Why over alternatives:** Simpler to implement and understand for educational purposes; inefficient for large lists, so reserved for small collections only.

## 3. Summary Table

| Structure / Algorithm | Use Case | Complexity | Chosen Over |
|-----------------------|----------|------------|-------------|
| HashMap | Entity lookup by ID | O(1) avg | ArrayList (O(n) lookup) |
| ArrayList | Ordered lists, transactions | O(1) get, amortized O(1) add | LinkedList (O(n) index) |
| HashSet | Uniqueness, low-stock dedup | O(1) avg | ArrayList.contains (O(n)) |
| Queue | Transaction audit log | O(1) offer/poll | Stack (wrong order) |
| Stack | Undo, UI back-stack | O(1) push/pop | Queue (wrong order) |
| Linear Search | Search by name | O(n) | HashMap (name not unique) |
| Binary Search | Sorted ID lookup | O(log n) | HashMap preferred for ID |
| Merge Sort | Reports by value/name/date | O(n log n) | Quick Sort (worst-case O(n²)) |
| Insertion/Selection Sort | Small alert lists | O(n²) | Merge Sort (too heavy for tiny lists) |

## 4. Complexity Guiding Principle
Prefer O(1) or O(log n) for frequent operations (lookup by ID, recording a transaction). Accept O(n) or O(n log n) for infrequent operations (reports, searches by non-unique fields). All custom implementations will live in `data_structure` so the rest of the system remains independent of the underlying choice.
