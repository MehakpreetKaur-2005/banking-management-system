# Functional Requirements

## Banking Management System Using Java

**Project Type:** University Software Engineering Project
**Document:** Functional Requirements Specification
**Version:** 1.0
**Technology:** Java 17, Java Swing, JDBC, MySQL 8
**Architecture:** Three-Tier Architecture
**Related Document:** `docs/SRS.md`
**Related Document:** `docs/requirements/requirement-analysis.md`

---

# 1. Introduction

## 1.1 Purpose

This document defines the detailed **Functional Requirements** for the Banking Management System.

Functional requirements specify the services, operations, inputs, outputs, validations, and system behaviors that the application shall provide to its users.

Each requirement has been assigned a unique identifier to support:

* Requirement traceability
* System design
* Implementation
* Test case development
* Defect tracking
* Project documentation

The functional requirements defined in this document shall serve as the baseline for the subsequent design and implementation phases.

---

# 2. Functional Requirement Identification

Each functional requirement follows the naming convention:

```text
FR-<MODULE>-<NUMBER>
```

Where:

```text
FR      = Functional Requirement
MODULE  = Functional module
NUMBER  = Sequential requirement number
```

Examples:

```text
FR-AUTH-001
FR-CUST-001
FR-ACC-001
FR-DEP-001
FR-WD-001
FR-TRF-001
```

---

# 3. Functional Module Overview

The system is divided into the following functional modules:

```text
Banking Management System
│
├── Authentication
│
├── Customer Management
│
├── Account Management
│
├── Deposit Management
│
├── Withdrawal Management
│
├── Fund Transfer
│
├── Balance Enquiry
│
├── Transaction Management
│
├── Dashboard
│
├── Account Closure
│
└── Interest Calculation
```

---

# 4. Authentication Requirements

## FR-AUTH-001 — Employee Login

The system shall provide a login interface through which an employee can enter their username and password.

### Input

* Username
* Password

### Processing

The system shall validate the supplied credentials against the employee records stored in the database.

### Output

If authentication succeeds, the system shall allow access to the main dashboard.

If authentication fails, the system shall display an appropriate error message.

---

## FR-AUTH-002 — Mandatory Login Credentials

The system shall require both username and password fields to be populated before authentication is attempted.

If either field is empty, the system shall reject the login request and display a validation message.

---

## FR-AUTH-003 — Credential Validation

The system shall verify the supplied username and password against the stored employee credentials.

Invalid credentials shall not grant access to protected functionality.

---

## FR-AUTH-004 — Employee Status Validation

The system shall verify that the employee account is active before granting access.

An inactive employee account shall not be permitted to log into the system.

---

## FR-AUTH-005 — Authentication Failure Handling

If authentication fails, the system shall:

1. Reject the login attempt.
2. Keep the user on the login screen.
3. Display an appropriate error message.
4. Avoid exposing sensitive authentication information.

---

## FR-AUTH-006 — Successful Authentication

After successful authentication, the system shall:

1. Identify the authenticated employee.
2. Establish the authenticated application state.
3. Display the employee dashboard.
4. Allow access to permitted functionality.

---

## FR-AUTH-007 — Logout

The system shall provide a logout function.

When an employee logs out, the system shall:

1. End the authenticated session state.
2. Prevent access to protected functionality.
3. Return the employee to the login screen.

---

# 5. Customer Management Requirements

## FR-CUST-001 — Customer Registration

The system shall allow an authenticated employee to register a new customer.

The registration form shall collect the required customer information.

Possible information includes:

* Customer ID
* Full name
* Date of birth
* Gender
* Phone number
* Email
* Address

---

## FR-CUST-002 — Customer ID Uniqueness

The system shall ensure that every customer has a unique customer ID.

If the supplied customer ID already exists, the system shall reject the registration request.

---

## FR-CUST-003 — Customer Name Validation

The system shall require the customer's full name.

The system shall reject a registration request when the required name field is empty.

---

## FR-CUST-004 — Customer Contact Validation

The system shall validate customer contact information according to the application's defined validation rules.

Invalid phone numbers or email addresses shall be rejected.

---

## FR-CUST-005 — Customer Registration Persistence

After successful validation, the system shall store the customer information in the MySQL database.

The customer record shall remain available after the application is restarted.

---

## FR-CUST-006 — Customer Registration Confirmation

After successful registration, the system shall display a confirmation message containing the newly created customer identifier.

---

## FR-CUST-007 — Customer Search

The system shall allow an authenticated employee to search for customers.

Supported search criteria shall include:

* Customer ID
* Customer name
* Phone number
* Email

---

## FR-CUST-008 — Customer Search Results

The system shall display matching customer records.

The displayed information may include:

* Customer ID
* Full name
* Date of birth
* Phone
* Email
* Address
* Status

---

## FR-CUST-009 — Customer Not Found

If no customer matches the supplied search criteria, the system shall display an appropriate message.

The system shall not treat the absence of a search result as a system failure.

---

## FR-CUST-010 — Customer Update

The system shall allow an authenticated employee to update permitted customer information.

The system shall validate updated information before saving it.

---

## FR-CUST-011 — Customer Update Persistence

After a successful update, the system shall store the modified customer information in the database.

---

## FR-CUST-012 — Customer Update Confirmation

After successfully updating a customer, the system shall display a confirmation message.

---

# 6. Account Management Requirements

## FR-ACC-001 — Account Creation

The system shall allow an authenticated employee to open a bank account for an existing customer.

---

## FR-ACC-002 — Existing Customer Validation

Before creating an account, the system shall verify that the specified customer exists.

If the customer does not exist, account creation shall be rejected.

---

## FR-ACC-003 — Savings Account Creation

The system shall support creation of Savings Accounts.

A Savings Account shall be associated with exactly one customer.

---

## FR-ACC-004 — Current Account Creation

The system shall support creation of Current Accounts.

A Current Account shall be associated with exactly one customer.

---

## FR-ACC-005 — Account Number Generation

The system shall generate a unique account number when a new account is created.

The generated account number shall not duplicate an existing account number.

---

## FR-ACC-006 — Multiple Accounts Per Customer

The system shall allow a customer to own multiple bank accounts.

The system shall not restrict a customer to a single account.

---

## FR-ACC-007 — Account Type Validation

The system shall allow only supported account types.

Initially supported account types shall be:

```text
SAVINGS
CURRENT
```

---

## FR-ACC-008 — Account Status

The system shall maintain the status of each account.

Possible statuses may include:

```text
ACTIVE
CLOSED
```

Additional statuses may be introduced during implementation if required.

---

## FR-ACC-009 — Account Creation Persistence

After successful account creation, the system shall persist the account information in the database.

---

## FR-ACC-010 — Account Search

The system shall allow an authenticated employee to search for an account using the account number.

---

## FR-ACC-011 — Account Information Display

The system shall display relevant account information, including:

* Account number
* Customer ID
* Customer name
* Account type
* Balance
* Status
* Opening date

---

## FR-ACC-012 — Closed Account Restriction

The system shall prevent normal banking transactions from being performed on a closed account.

---

# 7. Deposit Requirements

## FR-DEP-001 — Deposit Initiation

The system shall allow an authenticated employee to initiate a deposit into an account.

---

## FR-DEP-002 — Account Existence Validation

Before processing a deposit, the system shall verify that the specified account exists.

---

## FR-DEP-003 — Account Status Validation

The system shall verify that the target account is active before processing a deposit.

Deposits into closed accounts shall be rejected.

---

## FR-DEP-004 — Deposit Amount Validation

The system shall require the deposit amount to be greater than zero.

The following values shall be rejected:

```text
0
Negative values
Non-numeric values
```

---

## FR-DEP-005 — Deposit Balance Update

After a successful deposit, the system shall increase the account balance by the deposited amount.

The calculation shall be:

```text
New Balance = Existing Balance + Deposit Amount
```

---

## FR-DEP-006 — Deposit Transaction Creation

After a successful deposit, the system shall create a transaction record.

The record shall contain at least:

* Transaction ID
* Account information
* Transaction type
* Amount
* Status
* Timestamp

---

## FR-DEP-007 — Deposit Transaction ID

Every successful deposit shall have a unique transaction ID.

---

## FR-DEP-008 — Deposit Confirmation

After successful processing, the system shall display:

* Account number
* Deposit amount
* Updated balance
* Transaction ID
* Transaction status

---

## FR-DEP-009 — Deposit Failure Handling

If a deposit cannot be completed, the system shall:

1. Prevent an incorrect balance update.
2. Return an appropriate failure status.
3. Display a meaningful error message.

---

# 8. Withdrawal Requirements

## FR-WD-001 — Withdrawal Initiation

The system shall allow an authenticated employee to initiate a withdrawal.

---

## FR-WD-002 — Account Existence Validation

The system shall verify that the specified account exists before processing a withdrawal.

---

## FR-WD-003 — Account Status Validation

The system shall verify that the account is active before processing a withdrawal.

Withdrawals from closed accounts shall be rejected.

---

## FR-WD-004 — Withdrawal Amount Validation

The system shall require the withdrawal amount to be greater than zero.

The system shall reject:

```text
0
Negative values
Non-numeric values
```

---

## FR-WD-005 — Savings Minimum Balance Validation

For a Savings Account, the system shall ensure that a withdrawal does not cause the balance to fall below:

```text
₹1,000
```

The withdrawal shall be rejected if:

```text
Existing Balance - Withdrawal Amount < ₹1,000
```

---

## FR-WD-006 — Current Account Overdraft Validation

For a Current Account, the system shall ensure that the account balance does not fall below:

```text
-₹5,000
```

The withdrawal shall be rejected if:

```text
Existing Balance - Withdrawal Amount < -₹5,000
```

---

## FR-WD-007 — Withdrawal Balance Update

After a successful withdrawal, the system shall decrease the account balance by the withdrawal amount.

```text
New Balance = Existing Balance - Withdrawal Amount
```

---

## FR-WD-008 — Withdrawal Transaction Creation

After a successful withdrawal, the system shall create a transaction record.

---

## FR-WD-009 — Withdrawal Transaction ID

Every successful withdrawal shall have a unique transaction ID.

---

## FR-WD-010 — Withdrawal Confirmation

After successful processing, the system shall display:

* Account number
* Withdrawal amount
* Updated balance
* Transaction ID
* Transaction status

---

## FR-WD-011 — Withdrawal Failure Handling

If a withdrawal violates an account rule, the system shall:

1. Reject the withdrawal.
2. Preserve the existing balance.
3. Display an appropriate error message.
4. Record a failed transaction status where required by the transaction logging policy.

---

# 9. Fund Transfer Requirements

## FR-TRF-001 — Transfer Initiation

The system shall allow an authenticated employee to initiate a transfer between two eligible accounts.

---

## FR-TRF-002 — Source Account Validation

The system shall verify that the source account exists.

---

## FR-TRF-003 — Destination Account Validation

The system shall verify that the destination account exists.

---

## FR-TRF-004 — Source Account Status Validation

The system shall verify that the source account is active.

---

## FR-TRF-005 — Destination Account Status Validation

The system shall verify that the destination account is active.

---

## FR-TRF-006 — Same Account Validation

The system shall reject a transfer when the source account and destination account are identical.

---

## FR-TRF-007 — Transfer Amount Validation

The transfer amount shall be greater than zero.

The system shall reject:

```text
0
Negative values
Non-numeric values
```

---

## FR-TRF-008 — Source Balance Validation

The system shall verify that the source account satisfies the applicable balance restriction before completing the transfer.

For Savings Accounts:

```text
Remaining Balance >= ₹1,000
```

For Current Accounts:

```text
Remaining Balance >= -₹5,000
```

---

## FR-TRF-009 — Transfer Debit

The system shall debit the transfer amount from the source account after all validations have passed.

---

## FR-TRF-010 — Transfer Credit

The system shall credit the transfer amount to the destination account after the source debit has been successfully processed.

---

## FR-TRF-011 — Atomic Transfer

The debit, credit, and required transaction-record operations shall be executed as a single database transaction.

The system shall commit the transaction only when all required operations succeed.

---

## FR-TRF-012 — Transfer Rollback

If any required operation within a transfer fails, the system shall roll back the database transaction.

The rollback shall prevent a partial transfer from being permanently applied.

---

## FR-TRF-013 — Transfer Transaction ID

The system shall generate a unique transaction ID for each transfer.

---

## FR-TRF-014 — Transfer Transaction Record

The system shall record transfer information including:

* Transaction ID
* Source account
* Destination account
* Amount
* Transaction type
* Status
* Timestamp
* Description/reference where applicable

---

## FR-TRF-015 — Transfer Confirmation

After successful completion, the system shall display:

* Source account
* Destination account
* Transfer amount
* Transaction ID
* Transaction status

---

## FR-TRF-016 — Transfer Failure

If a transfer fails, the system shall:

1. Prevent partial balance updates.
2. Roll back the database transaction.
3. Return an appropriate transaction status.
4. Display an understandable error message.

---

# 10. Balance Enquiry Requirements

## FR-BAL-001 — Balance Enquiry

The system shall allow an authenticated employee to retrieve the current balance of an account.

---

## FR-BAL-002 — Account Validation

The system shall verify that the specified account exists before displaying balance information.

---

## FR-BAL-003 — Balance Display

The system shall display:

* Account number
* Customer name
* Account type
* Current balance
* Account status

---

## FR-BAL-004 — Current Balance Accuracy

The balance displayed by the system shall reflect the latest successfully committed database state.

---

# 11. Transaction Management Requirements

## FR-TXN-001 — Transaction Record Creation

The system shall create transaction records for successful banking operations.

---

## FR-TXN-002 — Transaction Identifier

Each transaction shall have a unique transaction ID.

---

## FR-TXN-003 — Transaction Type

The system shall identify the type of transaction.

Supported transaction types shall include:

```text
DEPOSIT
WITHDRAWAL
TRANSFER
```

If interest calculation is implemented:

```text
INTEREST
```

may also be supported.

---

## FR-TXN-004 — Transaction Amount

The system shall store the amount associated with the transaction.

---

## FR-TXN-005 — Transaction Status

The system shall maintain a transaction status.

Possible statuses include:

```text
SUCCESS
FAILED
```

Additional statuses may be introduced if required during implementation.

---

## FR-TXN-006 — Transaction Timestamp

The system shall store the date and time at which the transaction was processed.

---

## FR-TXN-007 — Transaction Description

The system may store an optional description or reference associated with the transaction.

---

## FR-TXN-008 — Transaction History

The system shall allow authenticated employees to retrieve transaction history.

---

## FR-TXN-009 — Transaction Search

The system shall allow transaction history to be filtered using relevant criteria.

Possible criteria include:

* Transaction ID
* Account number
* Transaction type
* Transaction status
* Date range

---

## FR-TXN-010 — Transaction History Display

The system shall display transaction records in a structured format.

Possible columns include:

| Column              | Description                         |
| ------------------- | ----------------------------------- |
| Transaction ID      | Unique transaction identifier       |
| Source Account      | Account from which funds originated |
| Destination Account | Account receiving funds             |
| Type                | Transaction type                    |
| Amount              | Transaction amount                  |
| Status              | Transaction status                  |
| Timestamp           | Transaction date and time           |
| Description         | Optional transaction reference      |

---

# 12. Dashboard Requirements

## FR-DASH-001 — Dashboard Access

The system shall display the dashboard after successful employee authentication.

---

## FR-DASH-002 — Total Customer Count

The system shall display the total number of registered customers.

---

## FR-DASH-003 — Total Account Count

The system shall display the total number of accounts.

---

## FR-DASH-004 — Savings Account Count

The system shall display the number of Savings Accounts.

---

## FR-DASH-005 — Current Account Count

The system shall display the number of Current Accounts.

---

## FR-DASH-006 — Active Account Count

The system shall display the number of active accounts.

---

## FR-DASH-007 — Transaction Count

The system shall display the total number of recorded transactions.

---

## FR-DASH-008 — Deposit Statistics

The system may display aggregate deposit statistics.

---

## FR-DASH-009 — Withdrawal Statistics

The system may display aggregate withdrawal statistics.

---

## FR-DASH-010 — Database-Based Statistics

Dashboard statistics shall be derived from the current database state.

---

# 13. Account Closure Requirements

## FR-CLOSE-001 — Account Closure Initiation

The system shall allow an authenticated employee to initiate account closure for an eligible account.

---

## FR-CLOSE-002 — Account Existence Validation

The system shall verify that the account exists before attempting closure.

---

## FR-CLOSE-003 — Account Status Validation

The system shall verify that the account is currently active before allowing closure.

---

## FR-CLOSE-004 — Closure Confirmation

The system shall request confirmation before performing the account closure operation.

---

## FR-CLOSE-005 — Account Status Update

After successful closure, the system shall update the account status to:

```text
CLOSED
```

---

## FR-CLOSE-006 — Closed Account Restriction

After closure, the system shall prevent normal deposits, withdrawals, and transfers involving the closed account.

---

## FR-CLOSE-007 — Closure Record

The system shall preserve the account record after closure.

The account shall not be physically deleted solely as a result of the closure operation.

---

# 14. Interest Calculation Requirements

> **Status:** Optional Feature

## FR-INT-001 — Interest Eligibility

If interest calculation is implemented, the system shall determine which accounts are eligible for interest.

---

## FR-INT-002 — Interest Rate

The system shall use a defined interest rate for eligible accounts.

The interest rate should be configurable rather than hard-coded throughout the application.

---

## FR-INT-003 — Interest Calculation

The system shall calculate interest according to the selected calculation rule.

The exact formula shall be documented during detailed design.

---

## FR-INT-004 — Interest Balance Update

After successful interest processing, the system shall update the eligible account balance.

---

## FR-INT-005 — Interest Transaction

The system shall create a transaction record for processed interest.

---

# 15. Validation Requirements

## FR-VAL-001 — Required Field Validation

The system shall validate all mandatory fields before processing a request.

---

## FR-VAL-002 — Numeric Validation

Fields requiring numeric values shall reject non-numeric input.

---

## FR-VAL-003 — Positive Amount Validation

Financial transaction amounts shall be greater than zero.

---

## FR-VAL-004 — Identifier Validation

Customer IDs and account numbers shall be validated before performing operations that depend on them.

---

## FR-VAL-005 — Email Validation

Email fields shall follow the application's defined email validation rules.

---

## FR-VAL-006 — Phone Validation

Phone fields shall follow the application's defined phone validation rules.

---

## FR-VAL-007 — Duplicate Data Validation

The system shall reject duplicate values for fields that require uniqueness.

Examples include:

* Customer ID
* Account number
* Employee username
* Transaction ID

---

# 16. Error Handling Requirements

## FR-ERR-001 — User-Friendly Errors

The system shall display understandable error messages for user-correctable errors.

---

## FR-ERR-002 — Database Error Handling

The system shall handle database failures without exposing raw SQL exceptions to end users.

---

## FR-ERR-003 — Missing Record Handling

The system shall provide appropriate messages when requested records do not exist.

---

## FR-ERR-004 — Invalid Operation Handling

The system shall reject operations that violate defined business rules.

---

## FR-ERR-005 — Transaction Failure Handling

If a banking transaction fails, the system shall ensure that no invalid partial state is committed.

---

# 17. Database Interaction Requirements

## FR-DB-001 — JDBC Connectivity

The application shall communicate with MySQL using JDBC.

---

## FR-DB-002 — Prepared Statements

The system shall use parameterized SQL statements for database operations involving user input.

---

## FR-DB-003 — CRUD Operations

The data access layer shall support required CRUD operations for:

* Employees
* Customers
* Accounts
* Transactions

---

## FR-DB-004 — Database Transactions

Operations requiring multiple related database updates shall use SQL transactions.

---

## FR-DB-005 — Commit

The system shall commit database transactions only after all required operations have completed successfully.

---

## FR-DB-006 — Rollback

The system shall roll back database transactions when a required operation fails.

---

## FR-DB-007 — Referential Integrity

The system shall preserve relationships between customers, accounts, and transactions using appropriate database constraints.

---

# 18. Authorization Requirements

## FR-AUTHZ-001 — Protected Functionality

The system shall restrict banking operations to authenticated employees.

---

## FR-AUTHZ-002 — Employee Role

If role-based authorization is implemented, the system shall associate an employee with a defined role.

Possible roles include:

```text
EMPLOYEE
MANAGER
ADMIN
```

---

## FR-AUTHZ-003 — Role-Based Access

If multiple roles are implemented, the system shall restrict selected operations according to the permissions assigned to each role.

---

# 19. Multithreading Requirements

## FR-THREAD-001 — Concurrent Operation Demonstration

The system shall demonstrate concurrent execution of selected banking operations using Java threads.

---

## FR-THREAD-002 — Concurrent Transaction Safety

Concurrent transactions shall not result in an incorrectly calculated account balance.

---

## FR-THREAD-003 — Database Transaction Control

Concurrent operations affecting shared account data shall use appropriate database transaction control.

---

## FR-THREAD-004 — Thread-Safe Processing

The implementation shall ensure that shared banking data is not incorrectly overwritten because of concurrent operations.

---

# 20. Collection Requirements

## FR-COLL-001 — Collection Usage

The system shall use Java Collections where appropriate for handling groups of domain objects.

Possible examples include:

```text
List<Customer>
List<Account>
List<Transaction>
Map<Long, Customer>
Map<String, Account>
```

---

## FR-COLL-002 — Search Results

Customer, account, and transaction search results may be represented using appropriate Java Collection types before being displayed by the UI.

---

# 21. Object-Oriented Requirements

## FR-OOP-001 — Encapsulation

The system shall encapsulate domain object state using appropriate access modifiers and methods.

---

## FR-OOP-002 — Abstraction

The system shall use abstraction to represent common account behavior.

---

## FR-OOP-003 — Inheritance

The system shall demonstrate inheritance between the common account abstraction and specific account types.

Expected relationship:

```text
Account
├── SavingsAccount
└── CurrentAccount
```

---

## FR-OOP-004 — Interfaces

The system shall demonstrate the use of interfaces for appropriate reusable behavior.

An example is:

```text
InterestCalculable
```

---

## FR-OOP-005 — Polymorphism

The system shall support polymorphic references to account types.

For example:

```text
Account account;
```

may refer to:

```text
SavingsAccount
```

or:

```text
CurrentAccount
```

---

## FR-OOP-006 — Method Overloading

The system shall demonstrate method overloading where appropriate.

Example:

```text
searchCustomer(int customerId)

searchCustomer(String name)

searchCustomer(String name, String phone)
```

---

## FR-OOP-007 — Method Overriding

Account subclasses shall override appropriate inherited behavior where account-specific behavior differs.

---

## FR-OOP-008 — Object Methods

Relevant domain classes shall implement appropriate versions of:

```text
toString()
equals()
hashCode()
```

---

# 22. Logging Requirements

## FR-LOG-001 — Application Events

The system should record important application events for debugging and audit purposes.

Examples include:

* Login success
* Login failure
* Customer creation
* Customer update
* Account creation
* Deposit
* Withdrawal
* Transfer
* Account closure
* Transaction failure
* Database errors

---

## FR-LOG-002 — Sensitive Data Protection

The system shall not log sensitive information such as plaintext passwords.

---

# 23. Functional Requirements by Priority

## 23.1 Must Have

The following requirements are essential to the initial working system:

### Authentication

* FR-AUTH-001
* FR-AUTH-002
* FR-AUTH-003
* FR-AUTH-004
* FR-AUTH-005
* FR-AUTH-006
* FR-AUTH-007

### Customer Management

* FR-CUST-001
* FR-CUST-002
* FR-CUST-003
* FR-CUST-004
* FR-CUST-005
* FR-CUST-007
* FR-CUST-008
* FR-CUST-009
* FR-CUST-010
* FR-CUST-011
* FR-CUST-012

### Account Management

* FR-ACC-001
* FR-ACC-002
* FR-ACC-003
* FR-ACC-004
* FR-ACC-005
* FR-ACC-006
* FR-ACC-007
* FR-ACC-008
* FR-ACC-009
* FR-ACC-010
* FR-ACC-011
* FR-ACC-012

### Transactions

* FR-DEP-001 through FR-DEP-009
* FR-WD-001 through FR-WD-011
* FR-TRF-001 through FR-TRF-016
* FR-BAL-001 through FR-BAL-004
* FR-TXN-001 through FR-TXN-010

### Database

* FR-DB-001 through FR-DB-007

### Validation and Error Handling

* FR-VAL-001 through FR-VAL-007
* FR-ERR-001 through FR-ERR-005

---

## 23.2 Should Have

The following requirements should be implemented if development time permits:

* FR-DASH-001 through FR-DASH-010
* FR-CLOSE-001 through FR-CLOSE-007
* FR-AUTHZ-001 through FR-AUTHZ-003
* FR-LOG-001
* FR-LOG-002

---

## 23.3 Could Have

The following requirements are optional:

* FR-INT-001 through FR-INT-005
* Advanced transaction filtering
* Additional reporting
* Data export

---

# 24. Functional Requirement Traceability

The functional requirements will be traced to business rules and use cases.

Example:

| Business Rule | Functional Requirement                           | Use Case              |
| ------------- | ------------------------------------------------ | --------------------- |
| BR-001        | FR-CUST-002                                      | UC-02                 |
| BR-002        | FR-ACC-006                                       | UC-05 / UC-06         |
| BR-003        | FR-ACC-005                                       | UC-05 / UC-06         |
| BR-004        | FR-WD-005                                        | UC-08                 |
| BR-005        | FR-WD-006                                        | UC-08                 |
| BR-006        | FR-DEP-004 / FR-WD-004 / FR-TRF-007              | UC-07 / UC-08 / UC-09 |
| BR-007        | FR-DEP-003 / FR-WD-003 / FR-TRF-004 / FR-TRF-005 | UC-07 / UC-08 / UC-09 |
| BR-010        | FR-TRF-006                                       | UC-09                 |
| BR-011        | FR-TRF-011 / FR-TRF-012                          | UC-09                 |
| BR-012        | FR-DEP-007 / FR-WD-009 / FR-TRF-013              | Banking Transactions  |
| BR-015        | FR-ACC-012                                       | UC-07 / UC-08 / UC-09 |

---

# 25. Functional Requirement Verification Approach

Each functional requirement shall eventually be verified using one or more of the following methods:

* Functional testing
* Unit testing
* Integration testing
* Database testing
* UI testing
* Negative testing
* Concurrency testing

Example:

### Requirement

```text
FR-WD-005
```

### Verification

Test a Savings Account withdrawal where:

```text
Balance = ₹2,000
Withdrawal = ₹1,500
```

Expected result:

```text
Transaction rejected
```

because:

```text
Remaining Balance = ₹500
```

which is below the required minimum of ₹1,000.

---

# 26. Functional Requirement Quality Criteria

Every functional requirement in this document should satisfy the following criteria:

## Clear

The requirement describes one understandable system behavior.

## Testable

A tester can determine whether the requirement has been satisfied.

## Traceable

The requirement has a unique identifier.

## Consistent

The requirement does not contradict another requirement.

## Feasible

The requirement can be implemented using the selected technology stack.

## Necessary

The requirement contributes to the defined system scope.

---

# 27. Summary

The Banking Management System shall provide a complete set of functional capabilities for managing customers, accounts, and banking transactions.

The major functional capabilities are:

```text
Employee Authentication
        ↓
Customer Management
        ↓
Account Management
        ↓
Deposit / Withdrawal
        ↓
Fund Transfer
        ↓
Balance Enquiry
        ↓
Transaction History
        ↓
Dashboard
        ↓
Account Closure
        ↓
Optional Interest Calculation
```

The system shall enforce the defined business rules during these operations and shall maintain persistent information using MySQL.

Fund transfers shall use atomic database transactions to prevent partial updates.

The functional requirements defined in this document will serve as the baseline for:

* Use Case Specifications
* Use Case Diagram
* ER Diagram
* Database Design
* UML Class Diagram
* Sequence Diagrams
* Activity Diagrams
* Java Implementation
* Test Case Design
* Requirements Traceability Matrix

---

# 28. Next Development Artifact

The next requirements document to be created is:

```text
docs/requirements/non-functional-requirements.md
```

This document will define measurable requirements for:

* Performance
* Reliability
* Security
* Usability
* Maintainability
* Scalability
* Availability
* Portability
* Testability
* Data integrity

After that, the project will move to:

```text
Business Rules
        ↓
Use Case Specifications
        ↓
Use Case Diagram
```

---

**Document Status:** Completed — Functional Requirements Draft
**Version:** 1.0
**Previous Document:** `docs/requirements/requirement-analysis.md`
**Next Document:** `docs/requirements/non-functional-requirements.md`

---

**End of Functional Requirements**
