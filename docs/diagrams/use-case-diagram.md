# Banking Management System — Use Case Diagram

## 1. Overview

The Use Case Diagram represents the functional interactions between the users of the Banking Management System and the system itself.

The system has two primary actors:

- **Administrator** — manages employees, customers, accounts, and system-level operations.
- **Bank Employee** — manages customers, accounts, and banking transactions.

Customers are represented as system entities rather than direct system actors because the current system is designed as an internal banking management application used by bank employees and administrators.

---

## 2. Actors

### Administrator

The Administrator has access to system-level management functions.

Responsibilities include:

- Login
- Manage employees
- Manage customers
- Manage accounts
- View transactions
- View account information
- View customer information

### Bank Employee

The Bank Employee performs day-to-day banking operations.

Responsibilities include:

- Login
- Register customers
- View customer information
- Create customer accounts
- View account information
- Deposit money
- Withdraw money
- Transfer money
- View transaction history

---

## 3. Use Cases

### Authentication

- Login
- Logout

### Employee Management

- Create employee
- View employee
- Update employee
- Activate/Deactivate employee

### Customer Management

- Register customer
- View customer
- Update customer
- Activate/Deactivate customer

### Account Management

- Create account
- View account
- Update account
- Activate/Deactivate account

### Transaction Management

- Deposit money
- Withdraw money
- Transfer money
- View transaction history
- View transaction details

### System Management

- View dashboard
- View customer information
- View account information
- View employee information

---

## 4. Use Case Diagram

```mermaid
flowchart LR

    %% ============================================================
    %% Actors
    %% ============================================================

    Admin["👤 Administrator"]
    Employee["👤 Bank Employee"]

    %% ============================================================
    %% System Boundary
    %% ============================================================

    subgraph BMS["Banking Management System"]

        %% Authentication
        Login["Login"]
        Logout["Logout"]

        %% Dashboard
        Dashboard["View Dashboard"]

        %% Employee Management
        ManageEmployees["Manage Employees"]
        CreateEmployee["Create Employee"]
        ViewEmployee["View Employee"]
        UpdateEmployee["Update Employee"]
        EmployeeStatus["Activate / Deactivate Employee"]

        %% Customer Management
        ManageCustomers["Manage Customers"]
        RegisterCustomer["Register Customer"]
        ViewCustomer["View Customer"]
        UpdateCustomer["Update Customer"]
        CustomerStatus["Activate / Deactivate Customer"]

        %% Account Management
        ManageAccounts["Manage Accounts"]
        CreateAccount["Create Account"]
        ViewAccount["View Account"]
        UpdateAccount["Update Account"]
        AccountStatus["Activate / Deactivate Account"]

        %% Transaction Management
        ManageTransactions["Manage Transactions"]
        Deposit["Deposit Money"]
        Withdraw["Withdraw Money"]
        Transfer["Transfer Money"]
        TransactionHistory["View Transaction History"]
        TransactionDetails["View Transaction Details"]

    end

    %% ============================================================
    %% Administrator Relationships
    %% ============================================================

    Admin --> Login
    Admin --> Logout
    Admin --> Dashboard

    Admin --> ManageEmployees
    Admin --> ManageCustomers
    Admin --> ManageAccounts
    Admin --> ManageTransactions

    ManageEmployees --> CreateEmployee
    ManageEmployees --> ViewEmployee
    ManageEmployees --> UpdateEmployee
    ManageEmployees --> EmployeeStatus

    ManageCustomers --> RegisterCustomer
    ManageCustomers --> ViewCustomer
    ManageCustomers --> UpdateCustomer
    ManageCustomers --> CustomerStatus

    ManageAccounts --> CreateAccount
    ManageAccounts --> ViewAccount
    ManageAccounts --> UpdateAccount
    ManageAccounts --> AccountStatus

    ManageTransactions --> TransactionHistory
    ManageTransactions --> TransactionDetails

    %% ============================================================
    %% Bank Employee Relationships
    %% ============================================================

    Employee --> Login
    Employee --> Logout
    Employee --> Dashboard

    Employee --> ManageCustomers
    Employee --> ManageAccounts
    Employee --> ManageTransactions

    ManageCustomers --> RegisterCustomer
    ManageCustomers --> ViewCustomer
    ManageCustomers --> UpdateCustomer

    ManageAccounts --> CreateAccount
    ManageAccounts --> ViewAccount
    ManageAccounts --> UpdateAccount

    ManageTransactions --> Deposit
    ManageTransactions --> Withdraw
    ManageTransactions --> Transfer
    ManageTransactions --> TransactionHistory
    ManageTransactions --> TransactionDetails

    %% ============================================================
    %% Styling
    %% ============================================================

    classDef actor fill:#f5f5f5,stroke:#333,stroke-width:2px,color:#111;
    classDef usecase fill:#e8f0fe,stroke:#333,stroke-width:1px,color:#111;
    classDef system fill:#ffffff,stroke:#333,stroke-width:2px,color:#111;

    class Admin,Employee actor;
    class Login,Logout,Dashboard,ManageEmployees,CreateEmployee,ViewEmployee,UpdateEmployee,EmployeeStatus,ManageCustomers,RegisterCustomer,ViewCustomer,UpdateCustomer,CustomerStatus,ManageAccounts,CreateAccount,ViewAccount,UpdateAccount,AccountStatus,ManageTransactions,Deposit,Withdraw,Transfer,TransactionHistory,TransactionDetails usecase;