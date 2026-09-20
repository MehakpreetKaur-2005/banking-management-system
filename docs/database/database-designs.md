# Database Design

## Banking Management System Using Java

**Document:** Physical Database Design
**Version:** 1.0
**Database:** MySQL 8
**Connectivity:** JDBC
**Application:** Java 17 Desktop Application

---

# 1. Purpose

This document defines the physical database design for the Banking Management System.

The database design is derived from the approved:

* Software Requirements Specification
* Requirement Analysis
* Functional Requirements
* Non-Functional Requirements
* Business Rules
* Use Case Specifications
* Entity Relationship Diagram

The database will provide persistent storage for:

* Bank employees
* Customers
* Bank accounts
* Financial transactions

---

# 2. Database Objectives

The database shall:

1. Store banking information reliably.
2. Maintain referential integrity.
3. Prevent duplicate business identifiers.
4. Support customer-to-account relationships.
5. Support account-to-transaction relationships.
6. Support fund transfers.
7. Preserve transaction history.
8. Support transactional consistency.
9. Support concurrent banking operations.
10. Provide efficient searching and reporting.

---

# 3. Database Technology

| Property                   | Value              |
| -------------------------- | ------------------ |
| Database Management System | MySQL 8            |
| Storage Engine             | InnoDB             |
| Character Set              | utf8mb4            |
| Collation                  | utf8mb4_0900_ai_ci |
| Application Language       | Java 17            |
| Database API               | JDBC               |
| Monetary Java Type         | BigDecimal         |
| Monetary SQL Type          | DECIMAL            |

---

# 4. Database Name

The proposed database name is:

```text
banking_management_system
```

The database can be created using:

```sql
CREATE DATABASE banking_management_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;
```

The application will connect to this database through JDBC.

---

# 5. Database Tables

The initial implementation contains four primary tables:

```text
employees
customers
accounts
transactions
```

Relationship structure:

```text
customers
    │
    │ 1:N
    ▼
accounts
    │
    │ 1:N
    ▼
transactions

employees
    │
    │ 1:N
    ▼
transactions
```

---

# 6. Table: employees

## 6.1 Purpose

The `employees` table stores bank employee information used for authentication and transaction auditing.

---

## 6.2 Structure

| Column          | Data Type    | Null | Key | Description                  |
| --------------- | ------------ | ---- | --- | ---------------------------- |
| `employee_id`   | BIGINT       | NO   | PK  | Internal employee identifier |
| `employee_code` | VARCHAR(20)  | NO   | UK  | Unique employee code         |
| `username`      | VARCHAR(50)  | NO   | UK  | Login username               |
| `password_hash` | VARCHAR(255) | NO   |     | Hashed password              |
| `first_name`    | VARCHAR(50)  | NO   |     | Employee first name          |
| `last_name`     | VARCHAR(50)  | NO   |     | Employee last name           |
| `email`         | VARCHAR(100) | NO   | UK  | Employee email               |
| `phone`         | VARCHAR(20)  | YES  |     | Employee phone               |
| `role`          | VARCHAR(30)  | NO   |     | Employee role                |
| `status`        | VARCHAR(20)  | NO   |     | Employee status              |
| `created_at`    | DATETIME     | NO   |     | Creation timestamp           |
| `updated_at`    | DATETIME     | NO   |     | Last update timestamp        |

---

# 7. Employee Constraints

### Primary Key

```text
employee_id
```

### Unique Constraints

```text
employee_code
username
email
```

### Status

Allowed values:

```text
ACTIVE
INACTIVE
```

### Role

Initial role:

```text
EMPLOYEE
```

The design can later support:

```text
ADMIN
MANAGER
EMPLOYEE
```

if role-based access control is introduced.

---

# 8. Employee ID Generation

`employee_id` will use MySQL auto-increment:

```sql
BIGINT AUTO_INCREMENT
```

The application should not manually generate the internal employee ID.

---

# 9. Table: customers

## 9.1 Purpose

The `customers` table stores customer personal and contact information.

---

## 9.2 Structure

| Column          | Data Type    | Null | Key | Description                  |
| --------------- | ------------ | ---- | --- | ---------------------------- |
| `customer_id`   | BIGINT       | NO   | PK  | Internal customer identifier |
| `customer_code` | VARCHAR(20)  | NO   | UK  | Unique customer identifier   |
| `first_name`    | VARCHAR(50)  | NO   |     | Customer first name          |
| `last_name`     | VARCHAR(50)  | NO   |     | Customer last name           |
| `date_of_birth` | DATE         | NO   |     | Date of birth                |
| `gender`        | VARCHAR(20)  | YES  |     | Gender                       |
| `email`         | VARCHAR(100) | YES  |     | Email address                |
| `phone`         | VARCHAR(20)  | NO   |     | Phone number                 |
| `address`       | VARCHAR(255) | NO   |     | Address                      |
| `city`          | VARCHAR(50)  | NO   |     | City                         |
| `state`         | VARCHAR(50)  | NO   |     | State                        |
| `postal_code`   | VARCHAR(15)  | NO   |     | Postal code                  |
| `status`        | VARCHAR(20)  | NO   |     | Customer status              |
| `created_at`    | DATETIME     | NO   |     | Creation timestamp           |
| `updated_at`    | DATETIME     | NO   |     | Last update timestamp        |

---

# 10. Customer Constraints

### Primary Key

```text
customer_id
```

### Unique Constraint

```text
customer_code
```

### Customer Status

Allowed values:

```text
ACTIVE
INACTIVE
```

---

# 11. Customer Identifier

The system will use two identifiers:

```text
customer_id
customer_code
```

`customer_id` is the internal database identifier.

`customer_code` is the business identifier displayed to employees.

Example:

```text
customer_id   = 101
customer_code = CUST-000101
```

---

# 12. Table: accounts

## 12.1 Purpose

The `accounts` table stores bank account information associated with customers.

Supported account types:

```text
SAVINGS
CURRENT
```

---

## 12.2 Structure

| Column           | Data Type     | Null | Key | Description                 |
| ---------------- | ------------- | ---- | --- | --------------------------- |
| `account_id`     | BIGINT        | NO   | PK  | Internal account identifier |
| `account_number` | VARCHAR(20)   | NO   | UK  | Unique account number       |
| `customer_id`    | BIGINT        | NO   | FK  | Account owner               |
| `account_type`   | VARCHAR(20)   | NO   |     | Account type                |
| `balance`        | DECIMAL(15,2) | NO   |     | Current account balance     |
| `status`         | VARCHAR(20)   | NO   |     | Account status              |
| `opened_at`      | DATETIME      | NO   |     | Account opening time        |
| `closed_at`      | DATETIME      | YES  |     | Account closing time        |
| `updated_at`     | DATETIME      | NO   |     | Last update time            |

---

# 13. Account Constraints

### Primary Key

```text
account_id
```

### Unique Constraint

```text
account_number
```

### Foreign Key

```text
customer_id
    REFERENCES customers(customer_id)
```

### Account Type

Allowed values:

```text
SAVINGS
CURRENT
```

### Account Status

Allowed values:

```text
ACTIVE
CLOSED
```

---

# 14. Account Balance

The balance column will use:

```sql
DECIMAL(15,2)
```

This provides exact decimal representation suitable for the project's monetary values.

Java should use:

```java
BigDecimal
```

rather than:

```java
double
```

or:

```java
float
```

for financial calculations.

---

# 15. Savings Account Constraint

Savings accounts must maintain a minimum balance of:

```text
₹1,000.00
```

Therefore, after a withdrawal or outgoing transfer:

```text
balance >= 1000.00
```

This business rule will primarily be enforced by the service layer because the permitted balance depends on account type and transaction context.

---

# 16. Current Account Constraint

Current accounts support overdrafts up to:

```text
₹5,000.00
```

Therefore:

```text
balance >= -5000.00
```

The service layer shall validate the balance before allowing a withdrawal or transfer.

---

# 17. Table: transactions

## 17.1 Purpose

The `transactions` table stores financial transaction records.

It provides the transaction history required by the system.

---

## 17.2 Structure

| Column                  | Data Type     | Null | Key | Description                      |
| ----------------------- | ------------- | ---- | --- | -------------------------------- |
| `transaction_id`        | BIGINT        | NO   | PK  | Internal transaction ID          |
| `transaction_reference` | VARCHAR(30)   | NO   | UK  | Unique transaction reference     |
| `account_id`            | BIGINT        | NO   | FK  | Primary account                  |
| `related_account_id`    | BIGINT        | YES  | FK  | Related account for transfers    |
| `employee_id`           | BIGINT        | NO   | FK  | Employee who performed operation |
| `transaction_type`      | VARCHAR(20)   | NO   |     | Transaction type                 |
| `amount`                | DECIMAL(15,2) | NO   |     | Transaction amount               |
| `status`                | VARCHAR(20)   | NO   |     | Transaction status               |
| `description`           | VARCHAR(255)  | YES  |     | Transaction description          |
| `transaction_date`      | DATETIME      | NO   |     | Transaction timestamp            |

---

# 18. Transaction Types

The initial transaction types are:

```text
DEPOSIT
WITHDRAWAL
TRANSFER
```

Optional:

```text
INTEREST
```

---

# 19. Transaction Status

Supported statuses:

```text
SUCCESS
FAILED
```

A successful transaction represents a completed financial operation.

A failed transaction represents an attempted operation that did not complete successfully.

---

# 20. Transaction Reference

Each transaction will have a unique business reference.

Example:

```text
TXN-20260920-000001
TXN-20260920-000002
TXN-20260920-000003
```

The internal primary key remains:

```text
transaction_id
```

---

# 21. Primary Keys

The database will use the following primary keys:

```text
employees.employee_id
customers.customer_id
accounts.account_id
transactions.transaction_id
```

All internal primary keys will use:

```sql
BIGINT AUTO_INCREMENT
```

---

# 22. Foreign Keys

The following foreign-key relationships will be created.

## Account → Customer

```text
accounts.customer_id
        ↓
customers.customer_id
```

---

## Transaction → Account

```text
transactions.account_id
        ↓
accounts.account_id
```

---

## Transaction → Related Account

```text
transactions.related_account_id
        ↓
accounts.account_id
```

---

## Transaction → Employee

```text
transactions.employee_id
        ↓
employees.employee_id
```

---

# 23. Referential Integrity

Foreign keys shall use referential integrity to prevent orphan records.

For example, an account cannot reference a customer that does not exist.

Similarly, a transaction cannot reference a non-existent account.

---

# 24. Delete Strategy

Financial data should not be physically deleted during normal application operation.

The system should prefer status-based lifecycle management.

For example:

```text
ACTIVE → CLOSED
```

instead of:

```text
DELETE FROM accounts
```

Transaction history should be preserved.

Foreign keys should therefore avoid destructive cascading deletes.

Recommended behavior:

```text
ON DELETE RESTRICT
```

---

# 25. Update Strategy

Foreign-key primary identifiers should generally not change.

Therefore:

```text
ON UPDATE RESTRICT
```

is appropriate for the initial implementation.

---

# 26. Index Design

Indexes will be created for frequently searched fields.

Recommended indexes:

```text
employees.username
employees.employee_code
customers.customer_code
customers.phone
customers.email
accounts.account_number
accounts.customer_id
transactions.account_id
transactions.transaction_date
transactions.transaction_reference
```

---

# 27. Composite Indexes

The following composite indexes may improve common queries.

### Transaction History

```text
(account_id, transaction_date)
```

This supports retrieving transaction history for a particular account ordered by time.

### Customer Account Search

```text
(customer_id, status)
```

This supports retrieving active accounts belonging to a customer.

The final SQL implementation should create only indexes justified by actual query requirements.

---

# 28. Monetary Precision

All monetary values will use:

```sql
DECIMAL(15,2)
```

Example:

```text
1000.00
5000.00
125000.75
```

The application layer will use:

```java
BigDecimal
```

for monetary operations.

---

# 29. Timestamp Strategy

The database will store timestamps using:

```sql
DATETIME
```

Relevant timestamps include:

```text
created_at
updated_at
opened_at
closed_at
transaction_date
```

The application and database should use a consistent timezone strategy.

---

# 30. Database Character Set

The database will use:

```text
utf8mb4
```

This supports a wide range of Unicode characters.

---

# 31. Storage Engine

All tables will use:

```text
InnoDB
```

InnoDB is required because the application depends on:

* Transactions
* Foreign keys
* Referential integrity
* Concurrency control

---

# 32. Transaction Management

Fund transfers must be atomic.

A transfer should follow:

```text
START TRANSACTION

Validate source account
        ↓
Validate destination account
        ↓
Validate amount
        ↓
Validate balance
        ↓
Debit source account
        ↓
Credit destination account
        ↓
Create transaction records
        ↓
COMMIT
```

If any step fails:

```text
ROLLBACK
```

---

# 33. Transfer Consistency

The system must prevent situations such as:

```text
Source account debited
        ↓
Destination account NOT credited
```

Therefore, both balance modifications must belong to the same database transaction.

---

# 34. Concurrent Transactions

The database must support concurrent transaction processing.

Potential problems include:

* Lost updates
* Race conditions
* Incorrect account balances
* Double withdrawals

The implementation will use appropriate JDBC transaction handling and database locking/isolation mechanisms.

The exact concurrency strategy will be finalized during Java implementation.

---

# 35. Balance Update Strategy

Account balances should be updated atomically.

The implementation should avoid:

```text
SELECT balance
Calculate new balance in Java
UPDATE balance
```

without appropriate transaction and concurrency controls.

Instead, the service/DAO layer should use transactional database operations with appropriate locking.

---

# 36. Failed Transactions

Failed transaction attempts should be handled according to the application's transaction logging policy.

A failed transaction may contain:

```text
transaction_reference
account_id
employee_id
transaction_type
amount
status = FAILED
description
transaction_date
```

The description may contain an appropriate failure reason.

Examples:

```text
Insufficient balance
Account not active
Invalid amount
Destination account not found
```

---

# 37. Customer-to-Account Relationship

Relationship:

```text
CUSTOMERS 1 ───────── N ACCOUNTS
```

Rules:

1. One customer can own multiple accounts.
2. Every account must belong to one customer.
3. An account cannot exist without a valid customer.
4. A customer may have zero or more accounts.

---

# 38. Account-to-Transaction Relationship

Relationship:

```text
ACCOUNTS 1 ───────── N TRANSACTIONS
```

Rules:

1. One account can have many transactions.
2. Every financial transaction belongs to an account.
3. Transaction history should be preserved.
4. Transactions should not be deleted during normal operations.

---

# 39. Employee-to-Transaction Relationship

Relationship:

```text
EMPLOYEES 1 ───────── N TRANSACTIONS
```

Rules:

1. One employee can perform many transactions.
2. Each transaction records the responsible employee.
3. Employee information supports transaction auditing.

---

# 40. Transfer Relationship

Transfers involve two accounts.

```text
Source Account
      │
      │ account_id
      ▼
Transaction
      ▲
      │ related_account_id
      │
Destination Account
```

The `related_account_id` is nullable because it is required only for operations involving another account, such as transfers.

---

# 41. Database Security

Database credentials shall not be hardcoded directly inside Java source code.

For example, avoid:

```java
String password = "mypassword";
```

Database credentials should be provided through configuration.

Possible approaches include:

```text
application.properties
environment variables
local configuration file
```

The chosen approach will be finalized during implementation.

---

# 42. JDBC Connection

The application will use JDBC to communicate with MySQL.

Conceptually:

```text
Java Swing
    ↓
Service Layer
    ↓
DAO Layer
    ↓
JDBC
    ↓
MySQL
```

The DAO layer should manage database operations.

---

# 43. DAO Mapping

| Table          | DAO              |
| -------------- | ---------------- |
| `employees`    | `EmployeeDAO`    |
| `customers`    | `CustomerDAO`    |
| `accounts`     | `AccountDAO`     |
| `transactions` | `TransactionDAO` |

---

# 44. Service Mapping

| Business Operation    | Service              |
| --------------------- | -------------------- |
| Employee Login        | `EmployeeService`    |
| Customer Registration | `CustomerService`    |
| Customer Search       | `CustomerService`    |
| Customer Update       | `CustomerService`    |
| Account Opening       | `AccountService`     |
| Deposit               | `TransactionService` |
| Withdrawal            | `TransactionService` |
| Fund Transfer         | `TransferService`    |
| Balance Enquiry       | `AccountService`     |
| Transaction History   | `TransactionService` |
| Dashboard             | `DashboardService`   |

---

# 45. Database Schema Dependency

The implementation order will be:

```text
Database Design
      ↓
SQL Schema
      ↓
Database Creation
      ↓
Sample Data
      ↓
JDBC Connection
      ↓
DAO Implementation
      ↓
Service Implementation
```

---

# 46. Proposed SQL Table Creation Order

The SQL scripts should create tables in dependency order:

```text
1. employees
2. customers
3. accounts
4. transactions
```

Reason:

```text
accounts → customers
transactions → accounts
transactions → employees
```

Therefore, referenced tables must exist before dependent tables.

---

# 47. Sample Database Data

Development data may include:

### Employees

```text
EMP-001
EMP-002
```

### Customers

```text
CUST-000001
CUST-000002
```

### Accounts

```text
1000000001
1000000002
```

### Transactions

```text
TXN-20260920-000001
TXN-20260920-000002
```

Sample data will be used only for development and testing.

---

# 48. Database Validation Rules

The application and database together shall enforce:

```text
Customer code must be unique
Account number must be unique
Transaction reference must be unique

Deposit amount > 0
Withdrawal amount > 0
Transfer amount > 0

Savings balance >= ₹1,000
Current balance >= -₹5,000

Only ACTIVE accounts can transact

Source and destination accounts must be different

Referenced customers must exist
Referenced accounts must exist
Referenced employees must exist
```

---

# 49. Application vs Database Validation

Validation will be divided between the application and database.

## Application Layer

Responsible for:

* User input validation
* Business rules
* Account-type-specific rules
* Transaction workflow
* Error messages

## Database Layer

Responsible for:

* Primary keys
* Unique constraints
* Foreign keys
* NOT NULL constraints
* Data types
* Basic database integrity

This separation avoids relying entirely on either layer.

---

# 50. Database Design Summary

The final initial schema consists of:

```text
┌──────────────┐
│  employees   │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌────────────────┐
│ transactions   │
└───────┬────────┘
        │
        │ N:1
        ▼
┌────────────────┐
│   accounts     │
└───────┬────────┘
        │
        │ N:1
        ▼
┌────────────────┐
│   customers    │
└────────────────┘
```

Transfer relationship:

```text
accounts
    ↑
    │ related_account_id
    │
transactions
    │
    │ account_id
    ↓
accounts
```

---

# 51. Final Schema

```text
employees
---------
PK employee_id
UK employee_code
UK username
UK email
password_hash
first_name
last_name
phone
role
status
created_at
updated_at


customers
---------
PK customer_id
UK customer_code
first_name
last_name
date_of_birth
gender
email
phone
address
city
state
postal_code
status
created_at
updated_at


accounts
--------
PK account_id
UK account_number
FK customer_id
account_type
balance
status
opened_at
closed_at
updated_at


transactions
------------
PK transaction_id
UK transaction_reference
FK account_id
FK related_account_id
FK employee_id
transaction_type
amount
status
description
transaction_date
```

---

# 52. Design Status

**Document:** Database Design
**Version:** 1.0
**Status:** Approved for SQL Implementation

### Previous Artifact

```text
docs/diagrams/er-diagram.md
```

### Next Artifact

```text
database/schema.sql
```

The next stage is to convert this physical database design into executable MySQL SQL scripts.

---

**End of Database Design**
