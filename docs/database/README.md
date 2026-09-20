# Database

This directory contains the MySQL database scripts for the Banking Management System.

## Database

```text
banking_management_system
```

## Technology

* MySQL 8
* InnoDB
* utf8mb4
* JDBC
* Java 17

## Files

### schema.sql

Contains:

* Database creation
* Table definitions
* Primary keys
* Foreign keys
* Unique constraints
* Check constraints
* Indexes

### seed.sql

Contains development/test data for:

* Employees
* Customers
* Accounts
* Transactions

## Table Structure

```text
employees
    │
    │ 1:N
    ▼
transactions
    ▲
    │ N:1
    │
accounts
    ▲
    │ N:1
    │
customers
```

## Execution Order

Run:

```text
1. schema.sql
2. seed.sql
```

## MySQL CLI

Example:

```bash
mysql -u root -p < database/schema.sql
```

Then:

```bash
mysql -u root -p banking_management_system < database/seed.sql
```

## Important

The seed data is for development and testing only.

Do not use the sample credentials or sample customer information in a production environment.

Database credentials should not be committed to GitHub.
