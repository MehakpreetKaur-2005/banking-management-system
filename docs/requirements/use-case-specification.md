# Use Case Specifications

## Banking Management System Using Java

**Project Type:** University Software Engineering Project
**Document:** Use Case Specification
**Version:** 1.0
**Technology:** Java 17, Java Swing, JDBC, MySQL 8
**Architecture:** Three-Tier Architecture

### Related Documents

* `docs/SRS.md`
* `docs/requirements/requirement-analysis.md`
* `docs/requirements/functional-requirements.md`
* `docs/requirements/non-functional-requirements.md`
* `docs/requirements/business-rules.md`

---

# 1. Introduction

## 1.1 Purpose

This document defines the detailed use cases for the Banking Management System.

A use case describes how an actor interacts with the system to accomplish a specific business goal.

The use cases defined in this document will be used as the foundation for:

* Use Case Diagram
* System Design
* UML Class Diagram
* Sequence Diagrams
* Activity Diagrams
* Swing GUI Design
* Service Layer Implementation
* Test Case Development

---

# 2. Actors

The Banking Management System primarily has one operational actor.

## 2.1 Bank Employee

The Bank Employee is the primary user of the application.

The employee can:

* Log in
* Register customers
* Search customers
* Update customer information
* Open accounts
* Deposit money
* Withdraw money
* Transfer funds
* View balances
* View transaction history
* View dashboard statistics
* Close accounts where permitted

---

## 2.2 Database System

The MySQL database is considered a supporting external system.

It stores:

* Employee information
* Customer information
* Account information
* Transaction information

The database is accessed through JDBC.

---

# 3. Use Case Naming Convention

Use cases follow the naming convention:

```text
UC-<NUMBER>
```

Examples:

```text
UC-001
UC-002
UC-003
```

---

# 4. Use Case List

| ID     | Use Case                        | Primary Actor        | Priority      |
| ------ | ------------------------------- | -------------------- | ------------- |
| UC-001 | Employee Login                  | Bank Employee        | Critical      |
| UC-002 | Logout                          | Bank Employee        | High          |
| UC-003 | Register Customer               | Bank Employee        | High          |
| UC-004 | Search Customer                 | Bank Employee        | High          |
| UC-005 | Update Customer                 | Bank Employee        | High          |
| UC-006 | Open Savings Account            | Bank Employee        | High          |
| UC-007 | Open Current Account            | Bank Employee        | High          |
| UC-008 | Deposit Money                   | Bank Employee        | Critical      |
| UC-009 | Withdraw Money                  | Bank Employee        | Critical      |
| UC-010 | Transfer Funds                  | Bank Employee        | Critical      |
| UC-011 | View Balance                    | Bank Employee        | High          |
| UC-012 | View Transaction History        | Bank Employee        | High          |
| UC-013 | View Dashboard Statistics       | Bank Employee        | Medium        |
| UC-014 | Close Account                   | Bank Employee        | Medium        |
| UC-015 | Calculate Interest              | Bank Employee/System | Optional      |
| UC-016 | Process Concurrent Transactions | System               | Demonstration |

---

# 5. Use Case Relationships

The major relationships between use cases are:

```text
                    Employee
                       │
                       ▼
                 Employee Login
                       │
                       ▼
                 Banking Dashboard
                       │
       ┌───────────────┼────────────────┐
       │               │                │
       ▼               ▼                ▼
 Customer          Account          Transactions
 Management        Management       Management
       │               │                │
       │               │       ┌────────┼────────┐
       │               │       │        │        │
       ▼               ▼       ▼        ▼        ▼
 Register          Open       Deposit  Withdraw Transfer
 Customer          Account
       │               │
       ▼               ▼
 Search / Update   Savings /
 Customer          Current
```

---

# 6. UC-001 — Employee Login

## 6.1 Description

Allows a registered bank employee to authenticate and access the banking management system.

## 6.2 Primary Actor

Bank Employee

## 6.3 Preconditions

* The application is running.
* The employee has a registered account.
* The database is accessible.

## 6.4 Trigger

The employee enters their login credentials and selects the Login option.

## 6.5 Main Success Flow

1. Employee opens the application.
2. System displays the login screen.
3. Employee enters username.
4. Employee enters password.
5. Employee selects **Login**.
6. System validates the input.
7. System retrieves the employee record from the database.
8. System verifies the credentials.
9. System verifies that the employee account is active.
10. System creates the authenticated application session.
11. System displays the dashboard.

## 6.6 Alternative Flows

### A1 — Empty Username

1. Employee leaves username empty.
2. System displays a validation message.
3. Login is not performed.

### A2 — Empty Password

1. Employee leaves password empty.
2. System displays a validation message.
3. Login is not performed.

### A3 — Invalid Credentials

1. Employee enters incorrect credentials.
2. System rejects authentication.
3. System displays an appropriate error message.

### A4 — Inactive Employee

1. Credentials are valid.
2. Employee account is inactive.
3. System denies access.

### A5 — Database Unavailable

1. Employee attempts login.
2. Database connection fails.
3. System displays a database availability error.
4. Login is not completed.

## 6.7 Postconditions

### Success

* Employee is authenticated.
* Dashboard is displayed.

### Failure

* Employee remains unauthenticated.

---

# 7. UC-002 — Logout

## 7.1 Description

Allows an authenticated employee to end the current application session.

## 7.2 Primary Actor

Bank Employee

## 7.3 Preconditions

* Employee is authenticated.

## 7.4 Trigger

Employee selects Logout.

## 7.5 Main Success Flow

1. Employee selects Logout.
2. System terminates the authenticated session.
3. System returns the employee to the login screen.
4. Protected banking operations become inaccessible.

## 7.6 Postconditions

* Employee is logged out.
* No authenticated session remains active.

---

# 8. UC-003 — Register Customer

## 8.1 Description

Allows an employee to create a new customer record.

## 8.2 Primary Actor

Bank Employee

## 8.3 Preconditions

* Employee is authenticated.
* Database is available.

## 8.4 Trigger

Employee selects **Register Customer**.

## 8.5 Main Success Flow

1. System displays the customer registration form.
2. Employee enters customer details.
3. System validates mandatory fields.
4. System validates the customer information.
5. System checks customer ID uniqueness if customer ID is employee-entered.
6. System creates the customer record.
7. System stores the record in the database.
8. System displays a success message.

## 8.6 Alternative Flows

### A1 — Missing Required Information

System rejects the form and identifies the missing fields.

### A2 — Duplicate Customer ID

System rejects registration because the customer ID already exists.

### A3 — Invalid Data

System rejects invalid customer information.

### A4 — Database Failure

System does not create a partial customer record and displays an appropriate error.

## 8.7 Postconditions

A valid customer record exists in the database.

---

# 9. UC-004 — Search Customer

## 9.1 Description

Allows an employee to search for an existing customer.

## 9.2 Primary Actor

Bank Employee

## 9.3 Preconditions

* Employee is authenticated.
* Customer data exists or may be searched.

## 9.4 Trigger

Employee enters a search criterion and selects Search.

## 9.5 Main Success Flow

1. Employee opens customer search.
2. Employee enters a search value.
3. System validates the search input.
4. System queries the database.
5. System retrieves matching customers.
6. System displays the results.

## 9.6 Alternative Flows

### A1 — Customer Not Found

System displays an appropriate "No customer found" message.

### A2 — Invalid Search Input

System requests valid search information.

### A3 — Database Error

System displays an appropriate error message.

## 9.7 Postconditions

Matching customer information is displayed.

---

# 10. UC-005 — Update Customer

## 10.1 Description

Allows an employee to update valid information belonging to an existing customer.

## 10.2 Primary Actor

Bank Employee

## 10.3 Preconditions

* Employee is authenticated.
* Customer exists.

## 10.4 Trigger

Employee selects an existing customer and chooses Update.

## 10.5 Main Success Flow

1. Employee searches for a customer.
2. System displays customer information.
3. Employee modifies permitted fields.
4. System validates the new information.
5. System updates the customer record.
6. System saves the changes.
7. System displays confirmation.

## 10.6 Alternative Flows

### A1 — Customer Not Found

Update operation is rejected.

### A2 — Invalid Information

System rejects invalid information.

### A3 — Database Failure

Changes are not committed.

## 10.7 Postconditions

The customer record contains the updated valid information.

---

# 11. UC-006 — Open Savings Account

## 11.1 Description

Allows an employee to create a Savings Account for an existing customer.

## 11.2 Primary Actor

Bank Employee

## 11.3 Preconditions

* Employee is authenticated.
* Customer exists.
* Database is available.

## 11.4 Trigger

Employee selects Open Savings Account.

## 11.5 Main Success Flow

1. Employee identifies the customer.
2. System verifies that the customer exists.
3. Employee selects Savings Account.
4. Employee provides the required account information.
5. System validates the account information.
6. System generates a unique account number.
7. System creates the account.
8. System sets the account status to ACTIVE.
9. System stores the account in the database.
10. System displays the new account number.

## 11.6 Business Rules

* Account must belong to an existing customer.
* Account number must be unique.
* Account type must be SAVINGS.
* Normal withdrawals must maintain ₹1,000 minimum balance.

## 11.7 Alternative Flows

### A1 — Customer Not Found

Account creation is rejected.

### A2 — Invalid Account Information

System displays validation errors.

### A3 — Database Failure

Account creation is rolled back.

## 11.8 Postconditions

A new active Savings Account exists for the customer.

---

# 12. UC-007 — Open Current Account

## 12.1 Description

Allows an employee to create a Current Account for an existing customer.

## 12.2 Primary Actor

Bank Employee

## 12.3 Preconditions

* Employee is authenticated.
* Customer exists.
* Database is available.

## 12.4 Trigger

Employee selects Open Current Account.

## 12.5 Main Success Flow

1. Employee identifies the customer.
2. System verifies the customer.
3. Employee selects Current Account.
4. Employee provides required information.
5. System validates the information.
6. System generates a unique account number.
7. System creates the Current Account.
8. System sets status to ACTIVE.
9. System stores the account.
10. System displays confirmation.

## 12.6 Business Rules

The Current Account shall permit overdrawing up to:

```text
₹5,000
```

Therefore:

```text
Minimum permitted balance = -₹5,000
```

## 12.7 Alternative Flows

* Customer does not exist.
* Invalid account information.
* Database operation fails.

## 12.8 Postconditions

A new active Current Account exists.

---

# 13. UC-008 — Deposit Money

## 13.1 Description

Allows an employee to deposit money into an active account.

## 13.2 Primary Actor

Bank Employee

## 13.3 Preconditions

* Employee is authenticated.
* Account exists.
* Account is active.
* Deposit amount is greater than zero.

## 13.4 Trigger

Employee submits a deposit request.

## 13.5 Main Success Flow

1. Employee enters or selects the account number.
2. System retrieves the account.
3. System verifies the account is active.
4. Employee enters the deposit amount.
5. System validates that the amount is greater than zero.
6. System calculates the new balance.
7. System updates the account balance.
8. System creates a transaction record.
9. System marks the transaction as SUCCESS.
10. System commits the database transaction.
11. System displays the updated balance.

## 13.6 Alternative Flows

### A1 — Account Not Found

Deposit is rejected.

### A2 — Account Closed

Deposit is rejected.

### A3 — Invalid Amount

Deposit is rejected.

### A4 — Database Failure

Changes are rolled back.

## 13.7 Postconditions

* Account balance is increased.
* Successful transaction is recorded.

---

# 14. UC-009 — Withdraw Money

## 14.1 Description

Allows an employee to withdraw money from an active account subject to the account's balance rules.

## 14.2 Primary Actor

Bank Employee

## 14.3 Preconditions

* Employee is authenticated.
* Account exists.
* Account is active.
* Amount is greater than zero.

## 14.4 Trigger

Employee submits a withdrawal request.

## 14.5 Main Success Flow

1. Employee enters the account number.
2. System retrieves the account.
3. System verifies account status.
4. Employee enters withdrawal amount.
5. System validates the amount.
6. System determines account type.
7. System checks the applicable balance restriction.
8. System calculates the resulting balance.
9. System updates the account balance.
10. System creates a transaction record.
11. System marks the transaction SUCCESS.
12. System commits the transaction.
13. System displays the updated balance.

## 14.6 Savings Account Rule

After withdrawal:

```text
Balance >= ₹1,000
```

## 14.7 Current Account Rule

After withdrawal:

```text
Balance >= -₹5,000
```

## 14.8 Alternative Flows

### A1 — Account Not Found

Withdrawal is rejected.

### A2 — Account Closed

Withdrawal is rejected.

### A3 — Invalid Amount

Withdrawal is rejected.

### A4 — Balance Restriction Violated

Withdrawal is rejected.

### A5 — Database Failure

The operation is rolled back.

## 14.9 Postconditions

### Success

* Account balance decreases.
* Transaction is recorded.

### Failure

* Account balance remains unchanged.
* Failed transaction may be recorded according to the transaction logging policy.

---

# 15. UC-010 — Transfer Funds

## 15.1 Description

Allows an employee to transfer money between two active accounts.

## 15.2 Primary Actor

Bank Employee

## 15.3 Preconditions

* Employee is authenticated.
* Source account exists.
* Destination account exists.
* Both accounts are active.
* Source and destination accounts are different.
* Transfer amount is greater than zero.
* Source account satisfies its balance restriction.

## 15.4 Trigger

Employee submits a transfer request.

## 15.5 Main Success Flow

1. Employee enters the source account number.
2. Employee enters the destination account number.
3. Employee enters the transfer amount.
4. System validates the source account.
5. System validates the destination account.
6. System verifies both accounts are active.
7. System verifies source and destination are different.
8. System validates the transfer amount.
9. System checks the source account's balance restriction.
10. System begins a database transaction.
11. System debits the source account.
12. System credits the destination account.
13. System creates the required transaction record(s).
14. System commits the database transaction.
15. System displays a transfer-success message.

## 15.6 Atomicity Requirement

The transfer must be atomic.

```text
BEGIN TRANSACTION

Debit Source
      +
Credit Destination
      +
Create Transaction Record

COMMIT
```

If any required operation fails:

```text
ROLLBACK
```

---

## 15.7 Alternative Flows

### A1 — Source Account Not Found

Transfer is rejected.

### A2 — Destination Account Not Found

Transfer is rejected.

### A3 — Source Account Closed

Transfer is rejected.

### A4 — Destination Account Closed

Transfer is rejected.

### A5 — Same Source and Destination

Transfer is rejected.

### A6 — Invalid Amount

Transfer is rejected.

### A7 — Insufficient Available Balance

Transfer is rejected.

### A8 — Database Failure

The transaction is rolled back.

### A9 — Destination Credit Failure

Source debit is rolled back.

## 15.8 Postconditions

### Success

* Source account balance decreases.
* Destination account balance increases.
* Transaction record is created.
* Database transaction is committed.

### Failure

* No partial transfer remains committed.
* Appropriate failure information is recorded where applicable.

---

# 16. UC-011 — View Balance

## 16.1 Description

Allows an employee to view the current balance of an account.

## 16.2 Primary Actor

Bank Employee

## 16.3 Preconditions

* Employee is authenticated.
* Account exists.

## 16.4 Trigger

Employee searches for an account and requests balance information.

## 16.5 Main Success Flow

1. Employee enters account number.
2. System retrieves account.
3. System retrieves current balance.
4. System displays balance.

## 16.6 Alternative Flows

### A1 — Account Not Found

System displays an appropriate error.

### A2 — Database Error

System displays an appropriate error.

## 16.7 Postconditions

The current account balance is displayed.

---

# 17. UC-012 — View Transaction History

## 17.1 Description

Allows an employee to view transaction history associated with an account.

## 17.2 Primary Actor

Bank Employee

## 17.3 Preconditions

* Employee is authenticated.
* Account exists.

## 17.4 Trigger

Employee requests transaction history.

## 17.5 Main Success Flow

1. Employee enters or selects an account.
2. System validates the account.
3. System retrieves transaction records.
4. System orders the records appropriately.
5. System displays transaction history.

## 17.6 Transaction Information

Transaction history may display:

* Transaction ID
* Account Number
* Transaction Type
* Amount
* Transaction Status
* Timestamp
* Related Account, where applicable

## 17.7 Alternative Flows

### A1 — Account Not Found

System displays an error.

### A2 — No Transactions

System displays an appropriate message.

### A3 — Database Failure

System displays an error.

## 17.8 Postconditions

Transaction history is displayed.

---

# 18. UC-013 — View Dashboard Statistics

## 18.1 Description

Allows an employee to view summarized banking statistics.

## 18.2 Primary Actor

Bank Employee

## 18.3 Preconditions

* Employee is authenticated.
* Database is available.

## 18.4 Trigger

Employee opens the dashboard.

## 18.5 Main Success Flow

1. Employee logs in.
2. System opens the dashboard.
3. System retrieves summary information.
4. System calculates or retrieves statistics.
5. System displays dashboard statistics.

## 18.6 Possible Statistics

The dashboard may display:

* Total customers
* Total accounts
* Total Savings Accounts
* Total Current Accounts
* Total active accounts
* Total closed accounts
* Total transaction count
* Successful transaction count
* Failed transaction count

## 18.7 Alternative Flows

### A1 — Database Unavailable

System displays a meaningful error and retains basic application functionality where possible.

## 18.8 Postconditions

Dashboard statistics are displayed.

---

# 19. UC-014 — Close Account

## 19.1 Description

Allows an employee to close an active account.

## 19.2 Primary Actor

Bank Employee

## 19.3 Preconditions

* Employee is authenticated.
* Account exists.
* Account is active.

## 19.4 Trigger

Employee selects Close Account.

## 19.5 Main Success Flow

1. Employee searches for the account.
2. System displays account details.
3. Employee selects Close Account.
4. System displays a confirmation prompt.
5. Employee confirms.
6. System validates the account status.
7. System changes account status to CLOSED.
8. System saves the change.
9. System displays confirmation.

## 19.6 Alternative Flows

### A1 — Account Not Found

Closure is rejected.

### A2 — Account Already Closed

System informs the employee that the account is already closed.

### A3 — Employee Cancels

No changes are made.

### A4 — Database Failure

The status change is not committed.

## 19.7 Postconditions

```text
Account Status = CLOSED
```

Historical transaction records remain available.

---

# 20. UC-015 — Calculate Interest

## 20.1 Description

Optional functionality that calculates and applies interest to eligible accounts.

## 20.2 Primary Actor

Bank Employee / System

## 20.3 Preconditions

* Interest feature is enabled.
* Eligible account exists.
* Account is active.
* Interest rate is configured.

## 20.4 Trigger

Employee or system initiates interest calculation.

## 20.5 Main Success Flow

1. System identifies eligible accounts.
2. System retrieves required balance information.
3. System retrieves the applicable interest rate.
4. System calculates interest.
5. System updates the account balance.
6. System creates an interest transaction.
7. System records the transaction as SUCCESS.

## 20.6 Alternative Flows

* Account is not eligible.
* Account is closed.
* Interest rate is invalid.
* Database operation fails.

## 20.7 Postconditions

The account balance is updated with the calculated interest where applicable.

---

# 21. UC-016 — Process Concurrent Transactions

## 21.1 Description

Demonstrates safe processing of multiple transactions executing concurrently.

This use case exists primarily to satisfy the project's multithreading and concurrency requirements.

## 21.2 Primary Actor

System

## 21.3 Preconditions

* Test accounts exist.
* Database is available.
* Multiple transaction tasks are configured.

## 21.4 Trigger

The application starts a concurrency demonstration or test.

## 21.5 Main Success Flow

1. System creates multiple transaction tasks.
2. Each task executes in a separate thread where appropriate.
3. Threads attempt banking operations.
4. Database transactions protect account consistency.
5. System waits for transaction completion.
6. System verifies final account balances.
7. System displays or logs the result.

## 21.6 Expected Result

Concurrent transactions shall not result in:

* Lost updates
* Incorrect balances
* Partial committed transfers
* Corrupted transaction records

## 21.7 Alternative Flows

### A1 — Transaction Conflict

The system handles the conflict according to the selected database transaction strategy.

### A2 — Database Failure

The affected transaction is rolled back.

---

# 22. Global Use Case Rules

The following rules apply across multiple use cases.

## 22.1 Authentication Rule

Protected use cases require an authenticated employee.

---

## 22.2 Account Status Rule

Normal banking transactions require an ACTIVE account.

---

## 22.3 Amount Rule

Financial transaction amounts must be greater than zero.

---

## 22.4 Transaction Rule

Successful financial operations shall create appropriate transaction records.

---

## 22.5 Atomicity Rule

Operations involving multiple related database changes shall use database transactions where atomicity is required.

---

## 22.6 Exception Rule

Business-rule violations shall be handled using appropriate application exceptions.

---

# 23. Use Case Dependency Matrix

| Use Case                        | Depends On                                  |
| ------------------------------- | ------------------------------------------- |
| UC-001 Login                    | Employee Database                           |
| UC-002 Logout                   | UC-001                                      |
| UC-003 Register Customer        | UC-001                                      |
| UC-004 Search Customer          | UC-001                                      |
| UC-005 Update Customer          | UC-001, UC-004                              |
| UC-006 Open Savings Account     | UC-001, UC-004                              |
| UC-007 Open Current Account     | UC-001, UC-004                              |
| UC-008 Deposit Money            | UC-001, Account                             |
| UC-009 Withdraw Money           | UC-001, Account                             |
| UC-010 Transfer Funds           | UC-001, Source Account, Destination Account |
| UC-011 View Balance             | UC-001, Account                             |
| UC-012 View Transaction History | UC-001, Account                             |
| UC-013 Dashboard Statistics     | UC-001                                      |
| UC-014 Close Account            | UC-001, Account                             |
| UC-015 Calculate Interest       | Account                                     |
| UC-016 Concurrent Transactions  | Account, Transaction                        |

---

# 24. Use Case to Business Rule Traceability

| Use Case                    | Relevant Business Rules              |
| --------------------------- | ------------------------------------ |
| UC-001 Login                | BR-AUTH-001 to BR-AUTH-005           |
| UC-002 Logout               | BR-AUTH-005                          |
| UC-003 Register Customer    | BR-CUST-001 to BR-CUST-007           |
| UC-004 Search Customer      | BR-CUST-001                          |
| UC-005 Update Customer      | BR-CUST-005                          |
| UC-006 Open Savings Account | BR-ACC-001 to BR-ACC-010, BR-SAV-001 |
| UC-007 Open Current Account | BR-ACC-001 to BR-ACC-010, BR-CUR-001 |
| UC-008 Deposit Money        | BR-DEP-001 to BR-DEP-007             |
| UC-009 Withdraw Money       | BR-WD-001 to BR-WD-009               |
| UC-010 Transfer Funds       | BR-TRF-001 to BR-TRF-014             |
| UC-011 View Balance         | BR-ACC-007                           |
| UC-012 Transaction History  | BR-TXN-001 to BR-TXN-008             |
| UC-013 Dashboard            | BR-TXN-001 to BR-TXN-004             |
| UC-014 Close Account        | BR-CLOSE-001 to BR-CLOSE-006         |
| UC-015 Interest             | BR-INT-001 to BR-INT-005             |
| UC-016 Concurrency          | BR-CON-001 to BR-CON-005             |

---

# 25. Use Case to Functional Requirement Traceability

| Use Case | Functional Requirements                  |
| -------- | ---------------------------------------- |
| UC-001   | Employee Authentication                  |
| UC-002   | Employee Logout                          |
| UC-003   | Customer Registration                    |
| UC-004   | Customer Search                          |
| UC-005   | Customer Update                          |
| UC-006   | Savings Account Creation                 |
| UC-007   | Current Account Creation                 |
| UC-008   | Deposit                                  |
| UC-009   | Withdrawal                               |
| UC-010   | Fund Transfer                            |
| UC-011   | Balance Enquiry                          |
| UC-012   | Transaction History                      |
| UC-013   | Dashboard                                |
| UC-014   | Account Closure                          |
| UC-015   | Interest Calculation                     |
| UC-016   | Multithreading / Concurrent Transactions |

---

# 26. Use Case Test Scenario Mapping

Each use case will later be translated into detailed test cases.

Example:

## UC-008 — Deposit Money

Potential test scenarios:

```text
TC-DEP-001 Valid deposit
TC-DEP-002 Zero deposit
TC-DEP-003 Negative deposit
TC-DEP-004 Deposit to non-existing account
TC-DEP-005 Deposit to closed account
TC-DEP-006 Database failure during deposit
```

---

## UC-009 — Withdraw Money

Potential test scenarios:

```text
TC-WD-001 Valid Savings withdrawal
TC-WD-002 Savings withdrawal below minimum balance
TC-WD-003 Valid Current withdrawal
TC-WD-004 Current withdrawal beyond overdraft limit
TC-WD-005 Zero withdrawal
TC-WD-006 Negative withdrawal
TC-WD-007 Withdrawal from closed account
```

---

## UC-010 — Transfer Funds

Potential test scenarios:

```text
TC-TRF-001 Valid transfer
TC-TRF-002 Invalid source account
TC-TRF-003 Invalid destination account
TC-TRF-004 Same source and destination
TC-TRF-005 Invalid transfer amount
TC-TRF-006 Insufficient balance
TC-TRF-007 Closed source account
TC-TRF-008 Closed destination account
TC-TRF-009 Successful atomic transfer
TC-TRF-010 Transfer rollback
TC-TRF-011 Concurrent transfers
```

---

# 27. Use Case Completion Criteria

A use case is considered successfully completed when:

1. All preconditions are satisfied.
2. Input validation succeeds.
3. Required business rules are satisfied.
4. Required database operations succeed.
5. Required transaction records are created.
6. Database transactions are committed where applicable.
7. The user receives appropriate feedback.
8. The resulting system state is consistent.

A failed use case shall:

1. Identify the failure condition.
2. Prevent invalid state changes.
3. Roll back database operations where required.
4. Display an appropriate message.
5. Record failure information where required.

---

# 28. Overall Use Case Model

The overall system can be represented conceptually as:

```text
                         ┌──────────────────────┐
                         │    Bank Employee     │
                         └──────────┬───────────┘
                                    │
                                    ▼
                            ┌───────────────┐
                            │ Employee Login│
                            └───────┬───────┘
                                    │
                                    ▼
                         ┌────────────────────┐
                         │     Dashboard      │
                         └─────────┬──────────┘
                                   │
             ┌─────────────────────┼─────────────────────┐
             │                     │                     │
             ▼                     ▼                     ▼
      Customer Management    Account Management    Transactions
             │                     │                     │
       ┌─────┼─────┐         ┌─────┴─────┐       ┌──────┼───────┐
       │     │     │         │           │       │      │       │
       ▼     ▼     ▼         ▼           ▼       ▼      ▼       ▼
    Register Search Update Savings     Current Deposit Withdraw Transfer
                         Account       Account
                                                        │
                                                        ▼
                                                Transaction History
                                                        │
                                                        ▼
                                                 Balance Enquiry
```

---

# 29. Future Extensions

The following use cases may be added in future versions:

* Employee Management
* Role-Based Authorization
* Beneficiary Management
* Scheduled Transfer
* Loan Management
* Fixed Deposit
* Recurring Deposit
* Customer Account Statement
* Report Generation
* Database Backup
* Audit Report

These are outside the initial implementation scope unless explicitly included later.

---

# 30. Conclusion

This document formally defines the use cases of the Banking Management System.

The use cases establish the interaction between the Bank Employee and the system and provide a bridge between requirements and technical design.

The defined use cases will be used to produce:

```text
Use Case Specifications
        ↓
Use Case Diagram
        ↓
ER Diagram
        ↓
Database Design
        ↓
UML Class Diagram
        ↓
Sequence Diagrams
        ↓
Activity Diagrams
        ↓
Swing Wireframes
        ↓
Java Implementation
        ↓
Testing
```

The most critical operational use cases are:

```text
UC-001 Employee Login
UC-003 Register Customer
UC-006 Open Savings Account
UC-007 Open Current Account
UC-008 Deposit Money
UC-009 Withdraw Money
UC-010 Transfer Funds
UC-011 View Balance
UC-012 View Transaction History
```

The **Transfer Funds** use case requires particular attention during implementation because it involves database transactions, atomicity, rollback, concurrency, and financial data integrity.

---

# 31. Document Status

**Status:** Completed — Use Case Specification
**Version:** 1.0

### Previous Documents

```text
docs/SRS.md
docs/requirements/requirement-analysis.md
docs/requirements/functional-requirements.md
docs/requirements/non-functional-requirements.md
docs/requirements/business-rules.md
```

### Next Document

```text
docs/diagrams/use-case-diagram.md
```

---

**End of Use Case Specifications**
