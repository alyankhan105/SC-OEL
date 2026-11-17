# Phase 5 Testing Report

## Environment and execution

- Java: JDK 21
- Test framework: JUnit Jupiter 5.14.2
- Build: Maven, configured in the project root `pom.xml`
- Command: `mvn test`
- Result: **21 tests passed, 0 failures, 0 errors, 0 skipped**

Maven was not installed on the workstation, so Maven 3.9.11 was run from a temporary directory. Its dependency cache and build output were kept under the ignored `build/` and `target/` directories. The report below lists automated JUnit results; no rows are claimed as a separately performed manual test.

## OEL-required scenarios

| ID | Test scenario | Input | Expected result | Actual result | Status |
|---|---|---|---|---|---|
| OEL-01 | Add valid item | `Widget`, quantity `4` through ConsoleUI | Item is stored and generated ID is displayed | UI displayed ID 1; service count became 1 | PASS — automated |
| OEL-02 | Add empty-name item | `null`, empty, or whitespace-only name through validator/model/service checks | Reject input; inventory remains unchanged | `IllegalArgumentException` thrown; service invalid-add check left count at 0 | PASS — automated |
| OEL-03 | Add negative quantity | Quantity `-2` through ConsoleUI; also `-1` through service | Reject input; item is not added | UI displayed validation message; service count remained 0 | PASS — automated |
| OEL-04 | Add non-numeric quantity through interface | Quantity `abc` through ConsoleUI | Show conversion error and continue; do not add item | UI displayed “Quantity must be a whole number”; service count remained 0 | PASS — automated |
| OEL-05 | View all items | Add `Monitor`, quantity `5`; request service snapshot | Return the stored item(s) without exposing the backing collection | Snapshot contained the stored item; mutation threw `UnsupportedOperationException` | PASS — automated |
| OEL-06 | View empty inventory | Select menu option `2` on an empty service | Display an empty-inventory message | UI displayed “Inventory is empty.” | PASS — automated |
| OEL-07 | Update existing item | Existing ID, name `New name`, quantity `4` | Update the same object and reflect new values | Existing object retained identity and returned updated name/quantity | PASS — automated |
| OEL-08 | Update non-existing item | ID `99`, name `Missing`, quantity `1` | Report no update (`false`) and preserve inventory | Service returned `false` | PASS — automated |
| OEL-09 | Delete existing item | Delete an existing item's ID | Remove item and return success | Service returned `true`; count became 0 | PASS — automated |
| OEL-10 | Delete non-existing item | Delete the removed item's ID | Report no deletion (`false`) | Service returned `false` | PASS — automated |

## Suite breakdown

| Test class | Tests | Failures | Errors | Skipped |
|---|---:|---:|---:|---:|
| `ItemTest` | 4 | 0 | 0 | 0 |
| `InventoryServiceTest` | 9 | 0 | 0 | 0 |
| `InputValidatorTest` | 4 | 0 | 0 | 0 |
| `ConsoleUITest` | 4 | 0 | 0 | 0 |
| **Total** | **21** | **0** | **0** | **0** |

## Coverage notes

- `ItemTest` checks construction, getters, setters, invalid data, and stock-status boundaries at quantities 0, 1, 10, and 11.
- `InventoryServiceTest` checks CRUD, unique IDs, post-deletion ID behavior, count, immutable snapshots, validation, and atomic failed updates.
- `InputValidatorTest` checks null, empty, whitespace-only, and valid names; negative, zero, and positive quantities; and invalid and valid IDs.
- `ConsoleUITest` uses injected input and captured output to check empty inventory, valid and invalid menu choices, adding/searching an item, malformed quantities, negative quantities, and exit.

## Defects and changes

No application behavior defects were reproduced by the test suite. `ConsoleUI` gained an overload that accepts a `Scanner` and `PrintStream`, allowing deterministic input and output in automated tests. The original one-argument constructor and its console behavior are preserved. No business-logic behavior was changed for this phase.
