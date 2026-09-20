# Sequence Diagram

## Banking Management System

The Sequence Diagram represents the interaction between users and the
different components of the Banking Management System over time.

It shows how requests move through the system from the user interface to
the backend application, business logic, and database.

The main components involved are:

- **Actor** – Admin, Employee, or Customer
- **Frontend** – User interface
- **Backend API** – Handles HTTP requests
- **Authentication / Business Logic** – Validates and processes requests
- **Database** – Stores and retrieves system data

---

# 1. General System Request Flow

The general request flow follows:

```text
User
  ↓
Frontend
  ↓
Backend API
  ↓
Business Logic
  ↓
Database
  ↓
Business Logic
  ↓
Backend API
  ↓
Frontend
  ↓
User
```

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Service as Business Logic
    participant DB as Database

    User->>Frontend: Perform operation
    Frontend->>API: Send API request
    API->>Service: Process request
    Service->>DB: Query / update data
    DB-->>Service: Return database result
    Service-->>API: Return processed result
    API-->>Frontend: Return API response
    Frontend-->>User: Display result
```

---

# 2. Login Sequence

The login sequence describes how a user authenticates with the system.

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Auth as Authentication Service
    participant DB as Database

    User->>Frontend: Enter username and password
    Frontend->>API: POST /login
    API->>Auth: Validate credentials
    Auth->>DB: Find employee/customer
    DB-->>Auth: Return user record

    alt Valid credentials
        Auth-->>API: Authentication successful
        API-->>Frontend: Return success / session
        Frontend-->>User: Open dashboard
    else Invalid credentials
        Auth-->>API: Authentication failed
        API-->>Frontend: Return authentication error
        Frontend-->>User: Display login error
    end
```

---

# 3. Customer Registration Sequence

This sequence represents the creation of a new customer.

```mermaid
sequenceDiagram

    actor Employee
    participant Frontend
    participant API as Backend API
    participant Service as Customer Service
    participant DB as Database

    Employee->>Frontend: Enter customer details
    Frontend->>API: POST /customers
    API->>Service: Create customer

    Service->>DB: Check existing customer
    DB-->>Service: Return search result

    alt Customer already exists
        Service-->>API: Customer already exists
        API-->>Frontend: Return validation error
        Frontend-->>Employee: Display error
    else Customer does not exist
        Service->>DB: Insert customer record
        DB-->>Service: Customer created
        Service-->>API: Return customer details
        API-->>Frontend: Return success response
        Frontend-->>Employee: Display registration success
    end
```

---

# 4. Account Creation Sequence

This sequence represents creating a new bank account for a customer.

```mermaid
sequenceDiagram

    actor Employee
    participant Frontend
    participant API as Backend API
    participant Service as Account Service
    participant DB as Database

    Employee->>Frontend: Enter account details
    Frontend->>API: POST /accounts
    API->>Service: Create account

    Service->>DB: Verify customer
    DB-->>Service: Customer record

    alt Customer does not exist
        Service-->>API: Customer not found
        API-->>Frontend: Return error
        Frontend-->>Employee: Display error
    else Customer exists
        Service->>Service: Generate account number
        Service->>DB: Insert account record
        DB-->>Service: Account created
        Service-->>API: Return account details
        API-->>Frontend: Return success response
        Frontend-->>Employee: Display account created
    end
```

---

# 5. Deposit Sequence

This sequence represents depositing money into an account.

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Service as Transaction Service
    participant DB as Database

    User->>Frontend: Enter account and deposit amount
    Frontend->>API: POST /transactions/deposit
    API->>Service: Process deposit

    Service->>DB: Find account
    DB-->>Service: Account details

    alt Account not found
        Service-->>API: Account not found
        API-->>Frontend: Return error
        Frontend-->>User: Display error
    else Account found
        Service->>Service: Validate account and amount
        Service->>DB: Update account balance
        DB-->>Service: Balance updated

        Service->>DB: Insert transaction record
        DB-->>Service: Transaction recorded

        Service-->>API: Deposit successful
        API-->>Frontend: Return transaction result
        Frontend-->>User: Display deposit success
    end
```

---

# 6. Withdrawal Sequence

This sequence represents withdrawing money from an account.

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Service as Transaction Service
    participant DB as Database

    User->>Frontend: Enter account and withdrawal amount
    Frontend->>API: POST /transactions/withdraw
    API->>Service: Process withdrawal

    Service->>DB: Find account
    DB-->>Service: Account details

    alt Account not found
        Service-->>API: Account not found
        API-->>Frontend: Return error
        Frontend-->>User: Display error
    else Account found
        Service->>Service: Validate account and amount
        Service->>Service: Check available balance

        alt Insufficient balance
            Service-->>API: Insufficient balance
            API-->>Frontend: Return error
            Frontend-->>User: Display insufficient balance
        else Sufficient balance
            Service->>DB: Update account balance
            DB-->>Service: Balance updated

            Service->>DB: Insert transaction record
            DB-->>Service: Transaction recorded

            Service-->>API: Withdrawal successful
            API-->>Frontend: Return transaction result
            Frontend-->>User: Display withdrawal success
        end
    end
```

---

# 7. Fund Transfer Sequence

This sequence represents transferring money between two accounts.

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Service as Transaction Service
    participant DB as Database

    User->>Frontend: Enter source, destination and amount
    Frontend->>API: POST /transactions/transfer
    API->>Service: Process transfer

    Service->>DB: Find source account
    DB-->>Service: Source account details

    Service->>DB: Find destination account
    DB-->>Service: Destination account details

    alt Source account not found
        Service-->>API: Source account not found
        API-->>Frontend: Return error
        Frontend-->>User: Display error
    else Destination account not found
        Service-->>API: Destination account not found
        API-->>Frontend: Return error
        Frontend-->>User: Display error
    else Both accounts exist
        Service->>Service: Validate accounts and amount
        Service->>Service: Check source balance

        alt Insufficient balance
            Service-->>API: Insufficient balance
            API-->>Frontend: Return error
            Frontend-->>User: Display error
        else Sufficient balance
            Service->>DB: Debit source account
            DB-->>Service: Source balance updated

            Service->>DB: Credit destination account
            DB-->>Service: Destination balance updated

            Service->>DB: Insert transfer transaction
            DB-->>Service: Transaction recorded

            Service-->>API: Transfer successful
            API-->>Frontend: Return transaction result
            Frontend-->>User: Display transfer success
        end
    end
```

---

# 8. Transaction History Sequence

This sequence represents retrieving transaction history for an account.

```mermaid
sequenceDiagram

    actor User
    participant Frontend
    participant API as Backend API
    participant Service as Transaction Service
    participant DB as Database

    User->>Frontend: Request transaction history
    Frontend->>API: GET /transactions
    API->>Service: Get transaction history
    Service->>DB: Query transactions
    DB-->>Service: Return transaction records
    Service-->>API: Return transaction history
    API-->>Frontend: Return transaction list
    Frontend-->>User: Display transaction history
```

---

# 9. Admin Employee Management Sequence

This sequence represents an Admin managing employee records.

```mermaid
sequenceDiagram

    actor Admin
    participant Frontend
    participant API as Backend API
    participant Service as Employee Service
    participant DB as Database

    Admin->>Frontend: Select employee management
    Frontend->>API: Request employee data
    API->>Service: Get employees
    Service->>DB: Query employees
    DB-->>Service: Employee records
    Service-->>API: Return employees
    API-->>Frontend: Display employee records

    Admin->>Frontend: Create / update employee
    Frontend->>API: Send employee data
    API->>Service: Validate employee data
    Service->>DB: Insert / update employee
    DB-->>Service: Operation result
    Service-->>API: Return result
    API-->>Frontend: Return response
    Frontend-->>Admin: Display operation result
```

---

# 10. Sequence of a Successful Fund Transfer

The complete successful transfer interaction can be summarized as:

```mermaid
sequenceDiagram

    actor User
    participant UI as Frontend
    participant API as Backend API
    participant Service as Transaction Service
    participant DB as MySQL Database

    User->>UI: Initiate transfer
    UI->>API: Transfer request
    API->>Service: Validate transfer
    Service->>DB: Get source account
    DB-->>Service: Source account
    Service->>DB: Get destination account
    DB-->>Service: Destination account

    Service->>Service: Validate balance

    Service->>DB: Debit source account
    DB-->>Service: Debit successful

    Service->>DB: Credit destination account
    DB-->>Service: Credit successful

    Service->>DB: Save transaction
    DB-->>Service: Transaction saved

    Service-->>API: Transfer completed
    API-->>UI: Success response
    UI-->>User: Display transfer confirmation
```

---

# Sequence Diagram Summary

| Operation | Main Interaction |
|---|---|
| Login | User → Frontend → API → Authentication → Database |
| Customer Registration | Employee → Frontend → API → Customer Service → Database |
| Account Creation | Employee → Frontend → API → Account Service → Database |
| Deposit | User → Frontend → API → Transaction Service → Database |
| Withdrawal | User → Frontend → API → Transaction Service → Database |
| Fund Transfer | User → Frontend → API → Transaction Service → Database |
| Transaction History | User → Frontend → API → Transaction Service → Database |
| Employee Management | Admin → Frontend → API → Employee Service → Database |

---

# Relationship with Other Diagrams

The Sequence Diagram provides the **interaction and time-based view** of the
Banking Management System.

It complements the other diagrams:

- **Architecture Diagram** – Defines the structural layers and components.
- **ER Diagram** – Defines database entities and relationships.
- **Use Case Diagram** – Defines actors and system functionality.
- **Activity Diagram** – Defines operational workflows.
- **Sequence Diagram** – Defines communication between actors and system components.
- **Class Diagram** – Defines application classes and their relationships.

The sequence diagrams demonstrate how the functional requirements represented
in the Use Case Diagram and Activity Diagram can be implemented through the
system architecture and database.