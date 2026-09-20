# Non-Functional Requirements

## Banking Management System Using Java

**Project Type:** University Software Engineering Project
**Document:** Non-Functional Requirements Specification
**Version:** 1.0
**Technology:** Java 17, Java Swing, JDBC, MySQL 8
**Architecture:** Three-Tier Architecture
**Related Document:** `docs/SRS.md`
**Related Document:** `docs/requirements/requirement-analysis.md`
**Related Document:** `docs/requirements/functional-requirements.md`

---

# 1. Introduction

## 1.1 Purpose

This document defines the **Non-Functional Requirements (NFRs)** for the Banking Management System.

While functional requirements define **what the system shall do**, non-functional requirements define **how the system shall perform**, including:

* Performance
* Security
* Reliability
* Availability
* Usability
* Maintainability
* Scalability
* Portability
* Compatibility
* Data integrity
* Testability
* Error handling
* Concurrency
* Recoverability

The requirements defined in this document establish quality expectations for the system and will guide the architecture, implementation, testing, and deployment phases.

---

# 2. Non-Functional Requirement Identification

Each non-functional requirement follows the naming convention:

```text
NFR-<CATEGORY>-<NUMBER>
```

Where:

```text
NFR       = Non-Functional Requirement
CATEGORY  = Requirement category
NUMBER    = Sequential requirement number
```

Examples:

```text
NFR-PERF-001
NFR-SEC-001
NFR-REL-001
NFR-USE-001
```

---

# 3. Non-Functional Requirement Categories

The system's non-functional requirements are organized into the following categories:

```text
Non-Functional Requirements
│
├── Performance
├── Security
├── Reliability
├── Availability
├── Usability
├── Maintainability
├── Scalability
├── Portability
├── Compatibility
├── Data Integrity
├── Concurrency
├── Testability
├── Recoverability
├── Logging
├── Error Handling
└── Documentation
```

---

# 4. Performance Requirements

Performance requirements define the expected responsiveness of the application under normal operating conditions.

## NFR-PERF-001 — Application Responsiveness

The system shall provide responsive interaction for normal user operations.

For typical operations such as:

* Login
* Customer search
* Customer registration
* Account search
* Balance enquiry

the application should normally respond within **3 seconds** under the expected academic test environment.

---

## NFR-PERF-002 — Transaction Processing Response

Deposit, withdrawal, and transfer operations should normally complete within **3 seconds** under normal database and system conditions.

This measurement excludes extraordinary delays caused by database server failure or network infrastructure failure.

---

## NFR-PERF-003 — Search Response

Customer, account, and transaction searches should normally return results within **3 seconds** for the expected project dataset.

---

## NFR-PERF-004 — Dashboard Loading

The dashboard should load its primary statistics within **3 seconds** under normal operating conditions.

---

## NFR-PERF-005 — UI Responsiveness

The Swing user interface shall remain responsive during normal application operations.

Long-running database operations should not unnecessarily block the Swing Event Dispatch Thread.

---

## NFR-PERF-006 — Database Query Efficiency

Database queries shall retrieve only the information required for the requested operation.

Queries should avoid unnecessary full-table processing where suitable indexes or filtering conditions can be used.

---

## NFR-PERF-007 — Resource Management

The application shall properly release JDBC resources after database operations.

The implementation should use appropriate mechanisms such as:

```java
try-with-resources
```

where applicable.

---

# 5. Security Requirements

Security requirements define controls for protecting employee access, credentials, and banking information.

## NFR-SEC-001 — Authentication

The system shall require employee authentication before allowing access to protected banking functionality.

---

## NFR-SEC-002 — Password Protection

Employee passwords shall not be stored as plaintext passwords in the database.

Passwords should be stored using an appropriate one-way hashing mechanism.

---

## NFR-SEC-003 — Password Confidentiality

Passwords shall not be:

* Displayed in plaintext after entry.
* Printed in application logs.
* Included in error messages.
* Stored in plaintext configuration files.

---

## NFR-SEC-004 — Authorization

The system shall restrict protected operations to authenticated employees.

If role-based access control is implemented, access shall additionally depend on the employee's assigned role.

---

## NFR-SEC-005 — SQL Injection Prevention

The application shall use parameterized SQL queries or `PreparedStatement` for database operations involving user-provided input.

The system shall not construct SQL queries by directly concatenating untrusted input.

---

## NFR-SEC-006 — Input Validation

User-provided data shall be validated before being processed or persisted.

Validation shall occur at the appropriate application layers.

---

## NFR-SEC-007 — Sensitive Information Protection

The system shall avoid exposing sensitive information through:

* GUI error messages
* Console output
* Log files
* Exception messages

---

## NFR-SEC-008 — Database Credentials

Database credentials shall not be hard-coded throughout application source code.

Configuration should be separated from application logic where practical.

---

## NFR-SEC-009 — Session Protection

The system shall maintain authenticated application state only while the employee is logged in.

After logout, protected operations shall no longer be accessible.

---

## NFR-SEC-010 — Unauthorized Access

Unauthorized users shall not be able to directly access banking operations through the application interface.

Business-layer validation should also prevent protected operations from being executed without an authenticated context where applicable.

---

# 6. Reliability Requirements

Reliability requirements define the system's ability to perform operations correctly and consistently.

## NFR-REL-001 — Transaction Consistency

The system shall maintain consistent account balances after successful banking transactions.

---

## NFR-REL-002 — Atomic Fund Transfer

Fund transfers shall be processed atomically.

A transfer shall either:

```text
COMPLETELY SUCCEED
```

or:

```text
COMPLETELY FAIL
```

Partial transfers shall not be permanently committed.

---

## NFR-REL-003 — Database Rollback

If a required operation within a database transaction fails, the system shall roll back the transaction where appropriate.

---

## NFR-REL-004 — Exception Handling

The application shall handle expected runtime and database exceptions without terminating unexpectedly.

---

## NFR-REL-005 — Data Persistence

Successfully committed customer, account, and transaction records shall remain available after application restart.

---

## NFR-REL-006 — Invalid Operation Prevention

The system shall prevent operations that violate defined business rules.

Examples include:

* Negative deposits
* Zero-value withdrawals
* Withdrawal below Savings minimum balance
* Current Account overdraft beyond the permitted limit
* Transfers from closed accounts

---

## NFR-REL-007 — Failure Isolation

A failure in one operation should not unnecessarily terminate the entire application.

The system should handle operation-specific failures at the appropriate application layer.

---

# 7. Availability Requirements

Because this is a desktop academic application, availability requirements are defined for the intended local environment.

## NFR-AVL-001 — Application Startup

The application shall start successfully when required Java and database dependencies are available.

---

## NFR-AVL-002 — Database Dependency

If the MySQL database is unavailable, the application shall detect the connection failure and display an understandable error message.

---

## NFR-AVL-003 — Graceful Failure

Database connectivity failures shall not cause an uncontrolled application crash.

---

## NFR-AVL-004 — Recovery After Database Restoration

After database connectivity is restored, the application should be capable of establishing a new valid database connection without requiring unnecessary application reinstallation or configuration changes.

---

# 8. Usability Requirements

The application shall provide a clear and understandable interface for bank employees.

## NFR-USE-001 — User-Friendly Interface

The Swing interface shall provide a consistent and understandable layout.

---

## NFR-USE-002 — Navigation

The application shall provide clear navigation between major functions.

Major functions should be accessible from the main dashboard or an appropriate navigation mechanism.

---

## NFR-USE-003 — Form Labels

Input fields shall have clear labels describing the information expected from the employee.

---

## NFR-USE-004 — Input Feedback

The system shall provide immediate or appropriate feedback when user input is invalid.

---

## NFR-USE-005 — Error Messages

Error messages shall be understandable to normal application users.

Technical implementation details such as raw SQL exceptions should not be displayed directly to users.

---

## NFR-USE-006 — Confirmation Messages

Successful operations shall provide confirmation feedback.

Examples include:

```text
Customer registered successfully.
Account created successfully.
Deposit completed successfully.
Withdrawal completed successfully.
Transfer completed successfully.
```

---

## NFR-USE-007 — Confirmation for Critical Operations

The system should request confirmation before critical or potentially irreversible operations such as account closure.

---

## NFR-USE-008 — Consistent UI

The application shall maintain consistent:

* Button naming
* Form layout
* Navigation
* Error handling
* Confirmation messages
* Data presentation

across screens.

---

## NFR-USE-009 — Readability

Text, labels, tables, and controls shall be presented in a readable format suitable for desktop use.

---

# 9. Maintainability Requirements

The system shall be designed so that future modifications can be performed with minimal impact on unrelated components.

## NFR-MAINT-001 — Layered Architecture

The system shall follow the defined three-tier architecture:

```text
Presentation Layer
        ↓
Business Layer
        ↓
Data Access Layer
        ↓
Database
```

---

## NFR-MAINT-002 — Separation of Responsibilities

The application shall separate:

* UI logic
* Business logic
* Database access
* Domain models
* Exception handling
* Utility functions

---

## NFR-MAINT-003 — Package Organization

The Java project shall use an organized package structure such as:

```text
ui
model
service
dao
exception
util
```

---

## NFR-MAINT-004 — Single Responsibility

Classes should have clearly defined responsibilities.

A class should not unnecessarily combine UI, business logic, and database access responsibilities.

---

## NFR-MAINT-005 — Code Readability

Source code shall use:

* Meaningful class names
* Meaningful method names
* Meaningful variable names
* Consistent formatting
* Appropriate comments

---

## NFR-MAINT-006 — Documentation

Important classes and public methods should include appropriate documentation where necessary.

---

## NFR-MAINT-007 — Low Coupling

The system should minimize unnecessary dependencies between layers and modules.

---

## NFR-MAINT-008 — High Cohesion

Classes and modules should group related responsibilities together.

---

## NFR-MAINT-009 — Reusability

Common functionality should be implemented in reusable components rather than duplicated across multiple classes.

---

# 10. Scalability Requirements

The system is designed primarily for an academic environment but should allow future extension.

## NFR-SCAL-001 — Customer Growth

The database design shall support an increasing number of customer records without requiring structural redesign for normal growth.

---

## NFR-SCAL-002 — Account Growth

The database shall support multiple accounts per customer.

---

## NFR-SCAL-003 — Transaction Growth

The transaction table shall support a growing number of transaction records.

---

## NFR-SCAL-004 — Feature Extension

The architecture should allow additional account types or banking operations to be added without major modification to unrelated components.

---

## NFR-SCAL-005 — Database Scalability

The database schema shall use appropriate primary keys, foreign keys, constraints, and indexes to support efficient retrieval as data volume increases.

---

# 11. Portability Requirements

## NFR-PORT-001 — Java Compatibility

The application shall be developed using:

```text
Java 17
```

---

## NFR-PORT-002 — Operating System

The application should be capable of running on operating systems that support Java 17 and the required MySQL JDBC driver.

---

## NFR-PORT-003 — IDE Independence

Although IntelliJ IDEA is the primary development environment, the project should use standard Java project structures and dependencies so that it can be built using other compatible Java development environments.

---

## NFR-PORT-004 — Database Configuration

Database connection details shall be configurable so that the application can be connected to an appropriate MySQL environment without modifying business logic.

---

# 12. Compatibility Requirements

## NFR-COMP-001 — MySQL Compatibility

The system shall be designed for:

```text
MySQL 8.x
```

---

## NFR-COMP-002 — JDBC Compatibility

The application shall use a MySQL-compatible JDBC driver appropriate for Java 17.

---

## NFR-COMP-003 — Swing Compatibility

The graphical user interface shall be implemented using standard Java Swing components supported by Java 17.

---

# 13. Data Integrity Requirements

Data integrity is critical because account balances and transaction records must remain consistent.

## NFR-DATA-001 — Primary Key Integrity

Each major entity shall have a unique primary key.

---

## NFR-DATA-002 — Foreign Key Integrity

Relationships between entities shall be enforced using appropriate foreign keys.

Examples include:

```text
Account → Customer
Transaction → Account
```

---

## NFR-DATA-003 — Unique Constraints

Fields requiring uniqueness shall have appropriate unique constraints.

Examples include:

* Customer ID
* Account number
* Employee username
* Transaction ID

---

## NFR-DATA-004 — Null Constraints

Required fields shall not allow `NULL` values where business rules require a value.

---

## NFR-DATA-005 — Amount Integrity

Financial amounts shall use an appropriate database numeric type capable of accurately representing monetary values.

The implementation should avoid using floating-point types such as `double` for persistent monetary calculations.

---

## NFR-DATA-006 — Balance Integrity

Account balances shall only be modified through controlled business operations.

---

## NFR-DATA-007 — Transaction Integrity

Transaction records shall accurately correspond to successfully processed banking operations.

---

## NFR-DATA-008 — Referential Integrity

The system shall prevent orphan records where database relationships require an existing parent record.

---

## NFR-DATA-009 — Historical Records

Transaction history should remain available even when an account is closed, subject to the system's retention policy.

---

# 14. Concurrency Requirements

The system shall account for concurrent operations affecting the same account.

## NFR-CON-001 — Concurrent Transactions

The system shall support demonstration of multiple banking operations executing concurrently.

---

## NFR-CON-002 — Balance Consistency

Concurrent operations shall not result in lost updates or an incorrectly calculated final balance.

---

## NFR-CON-003 — Database Isolation

The implementation shall use an appropriate database transaction isolation strategy for operations where concurrent access could affect correctness.

---

## NFR-CON-004 — Atomic Operations

Operations involving multiple dependent database updates shall execute atomically.

---

## NFR-CON-005 — Thread Safety

Shared application resources shall be accessed safely when multiple threads are involved.

---

## NFR-CON-006 — Swing Thread Safety

Swing UI updates shall follow Swing's threading model.

Long-running background operations should not unnecessarily execute on the Swing Event Dispatch Thread.

---

# 15. Testability Requirements

The system shall be designed so that individual components and workflows can be tested independently.

## NFR-TEST-001 — Unit Testability

Business-layer components should be structured so that their logic can be tested independently of the Swing interface.

---

## NFR-TEST-002 — DAO Testability

Database access operations should be separated into DAO classes so they can be tested independently.

---

## NFR-TEST-003 — Validation Testability

Input validation and business rules should be implemented in a manner that allows positive and negative test cases.

---

## NFR-TEST-004 — Transaction Testability

Fund transfers shall be testable for:

* Successful transfer
* Invalid source account
* Invalid destination account
* Insufficient available balance
* Closed account
* Same source and destination
* Database failure
* Rollback

---

## NFR-TEST-005 — Boundary Testing

The system shall support testing around business-rule boundaries.

Examples:

### Savings Account

```text
Balance = ₹1,000
```

### Current Account

```text
Balance = -₹5,000
```

Tests should verify behavior immediately above, at, and below these limits.

---

## NFR-TEST-006 — Error Scenario Testing

The application shall support testing of invalid inputs and expected exceptions without requiring modification of production logic.

---

# 16. Recoverability Requirements

## NFR-REC-001 — Transaction Rollback

Failed multi-step database operations shall be rolled back where appropriate.

---

## NFR-REC-002 — Application Error Recovery

The application should recover from expected operation-level errors without requiring a full restart.

---

## NFR-REC-003 — Database Failure Recovery

The application shall report database connection failures and allow subsequent operations to attempt reconnection where appropriate.

---

## NFR-REC-004 — Data Preservation

A failed transaction shall not overwrite or corrupt previously committed valid data.

---

# 17. Logging Requirements

## NFR-LOG-001 — Application Logging

The application should maintain logs for important operational events.

---

## NFR-LOG-002 — Error Logging

Unexpected application and database errors should be logged for debugging purposes.

---

## NFR-LOG-003 — Transaction Logging

Important transaction processing events should be traceable through transaction records.

---

## NFR-LOG-004 — Sensitive Data Exclusion

Logs shall not contain:

* Plaintext passwords
* Database passwords
* Other sensitive authentication credentials

---

# 18. Error Handling Requirements

## NFR-ERR-001 — Graceful Error Handling

The application shall handle expected errors without uncontrolled termination.

---

## NFR-ERR-002 — Meaningful Error Messages

Error messages shall describe the problem sufficiently for the employee to understand the required corrective action.

---

## NFR-ERR-003 — Technical Error Isolation

Technical details such as stack traces and raw SQL exceptions shall not normally be shown directly to end users.

---

## NFR-ERR-004 — Exception Layering

Exceptions should be handled at appropriate architectural boundaries.

For example:

```text
DAO Exception
      ↓
Service Layer
      ↓
Application Exception
      ↓
UI Error Message
```

---

# 19. Auditability Requirements

## NFR-AUD-001 — Transaction Traceability

Banking transactions shall be identifiable through unique transaction IDs.

---

## NFR-AUD-002 — Transaction Timestamp

Each transaction record shall contain a timestamp.

---

## NFR-AUD-003 — Transaction Status

Transactions shall maintain an appropriate status such as:

```text
SUCCESS
FAILED
```

---

## NFR-AUD-004 — Account History

The system shall retain transaction history associated with accounts.

---

## NFR-AUD-005 — Operation Traceability

Important banking operations should be traceable to the employee who performed them if employee association is included in the final transaction design.

---

# 20. Database Requirements

## NFR-DB-001 — Relational Database

The system shall use a relational database management system.

The selected database shall be:

```text
MySQL 8
```

---

## NFR-DB-002 — Normalization

The database schema shall be designed using appropriate normalization principles to minimize unnecessary data duplication.

---

## NFR-DB-003 — Referential Integrity

The database shall enforce valid relationships between related entities.

---

## NFR-DB-004 — Indexing

Appropriate indexes should be created for frequently searched or joined fields.

Potential indexed fields include:

```text
customer_id
account_number
transaction_id
username
```

---

## NFR-DB-005 — Transaction Support

The database shall support ACID transactions for operations such as fund transfers.

---

# 21. Architecture Requirements

## NFR-ARCH-001 — Three-Tier Architecture

The application shall follow a three-tier architecture consisting of:

```text
Presentation Layer
        ↓
Business Layer
        ↓
Data Access Layer
```

---

## NFR-ARCH-002 — Presentation Layer

The presentation layer shall be implemented using Java Swing.

It shall be responsible primarily for:

* User interaction
* Form input
* Displaying results
* Displaying errors
* Navigation

---

## NFR-ARCH-003 — Business Layer

The business layer shall contain:

* Banking rules
* Validation
* Transaction processing
* Account behavior
* Business exceptions

---

## NFR-ARCH-004 — Data Access Layer

The data access layer shall be responsible for:

* SQL execution
* JDBC connectivity
* CRUD operations
* Database transaction operations

---

## NFR-ARCH-005 — Layer Independence

The UI layer should not directly execute SQL queries.

Database operations should be performed through the DAO/data access layer.

---

## NFR-ARCH-006 — Dependency Direction

The architecture should maintain a clear dependency direction:

```text
UI
 ↓
Service
 ↓
DAO
 ↓
Database
```

---

# 22. Object-Oriented Design Requirements

## NFR-OOP-001 — Encapsulation

Domain object state shall be protected using appropriate access modifiers.

---

## NFR-OOP-002 — Abstraction

Common account behavior should be represented through an abstraction such as an abstract `Account` class.

---

## NFR-OOP-003 — Inheritance

The account hierarchy shall demonstrate inheritance.

```text
Account
├── SavingsAccount
└── CurrentAccount
```

---

## NFR-OOP-004 — Polymorphism

The implementation shall use polymorphism where appropriate to support account-specific behavior.

---

## NFR-OOP-005 — Interfaces

Interfaces should be used to represent reusable capabilities where appropriate.

---

## NFR-OOP-006 — Object Contracts

Domain objects should correctly implement:

```text
toString()
equals()
hashCode()
```

where appropriate.

---

# 23. Configuration Requirements

## NFR-CONFIG-001 — External Configuration

Database configuration should be separated from business logic.

---

## NFR-CONFIG-002 — Environment-Specific Configuration

The application should allow database configuration to be changed between development and testing environments without modifying core business logic.

---

## NFR-CONFIG-003 — Secure Configuration

Sensitive configuration values should not be committed directly to the public source repository.

---

# 24. Source Code Quality Requirements

## NFR-CODE-001 — Naming Conventions

Java naming conventions shall be followed.

Examples:

```text
ClassName
methodName()
variableName
CONSTANT_NAME
```

---

## NFR-CODE-002 — Formatting

Source code shall follow consistent indentation and formatting practices.

---

## NFR-CODE-003 — Comments

Comments shall be used where they improve understanding of complex logic.

Comments shall not unnecessarily explain obvious code.

---

## NFR-CODE-004 — Duplication

Unnecessary duplication of business logic shall be avoided.

---

## NFR-CODE-005 — Exception Safety

Exceptions shall be handled intentionally rather than silently ignored.

---

# 25. Documentation Requirements

## NFR-DOC-001 — Technical Documentation

The project shall maintain documentation covering:

* Requirements
* Architecture
* Database design
* UML diagrams
* Implementation
* Testing

---

## NFR-DOC-002 — API/Class Documentation

Important public classes and methods should contain appropriate Java documentation where necessary.

---

## NFR-DOC-003 — Database Documentation

The project documentation shall describe the database schema, relationships, constraints, and important SQL operations.

---

## NFR-DOC-004 — Setup Documentation

The project shall provide instructions explaining how to:

1. Install required software.
2. Configure MySQL.
3. Create the database.
4. Configure the application.
5. Build the project.
6. Run the application.

---

# 26. Backup and Data Protection Considerations

## NFR-BACK-001 — Database Backup

The project should document a method for backing up the MySQL database during development.

---

## NFR-BACK-002 — Source Code Backup

The Git repository shall serve as the primary version-control mechanism for source code and project documentation.

---

## NFR-BACK-003 — Credential Protection

Database credentials and other secrets shall not be committed to the Git repository.

---

# 27. Non-Functional Requirement Priorities

## 27.1 Critical

The following requirements are considered critical to system correctness:

* Transaction consistency
* Fund-transfer atomicity
* Data integrity
* Authentication
* SQL injection prevention
* Exception handling
* Database transaction management
* Balance consistency

---

## 27.2 High

The following requirements are important for a usable and maintainable application:

* Performance
* Usability
* Maintainability
* Testability
* Reliability
* Security
* Architecture separation

---

## 27.3 Medium

The following requirements support future growth:

* Scalability
* Portability
* Advanced logging
* Advanced configuration
* Extended reporting

---

# 28. Non-Functional Requirement Verification

Non-functional requirements shall be verified using appropriate testing and review methods.

| Category        | Verification Method                    |
| --------------- | -------------------------------------- |
| Performance     | Performance testing                    |
| Security        | Security review and negative testing   |
| Reliability     | Failure and recovery testing           |
| Availability    | Environment testing                    |
| Usability       | UI testing and user review             |
| Maintainability | Code review                            |
| Scalability     | Database/load testing where applicable |
| Portability     | Environment testing                    |
| Data Integrity  | Database constraint testing            |
| Concurrency     | Multithreaded transaction testing      |
| Testability     | Unit/integration test review           |
| Recoverability  | Failure and rollback testing           |
| Logging         | Log inspection                         |
| Documentation   | Documentation review                   |

---

# 29. Requirement Traceability

Non-functional requirements shall be traced to the appropriate system components and tests.

Example:

```text
NFR-SEC-005
SQL Injection Prevention
        ↓
DAO Layer
        ↓
PreparedStatement
        ↓
Security Test
```

Another example:

```text
NFR-REL-002
Atomic Fund Transfer
        ↓
TransferService
        ↓
JDBC Transaction
        ↓
COMMIT / ROLLBACK
        ↓
Transfer Integration Test
```

Another example:

```text
NFR-CON-002
Balance Consistency
        ↓
Transaction Processing
        ↓
Database Isolation / Locking
        ↓
Concurrent Transaction Test
```

---

# 30. NFR Summary

The Banking Management System shall satisfy the following major quality characteristics:

```text
                    Banking Management System
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
      Performance          Security           Reliability
          │                   │                   │
      Usability          Data Integrity       Availability
          │                   │                   │
   Maintainability       Concurrency          Testability
          │                   │                   │
      Scalability        Recoverability       Portability
```

The most important non-functional characteristics for this system are:

1. Data integrity
2. Transaction consistency
3. Security
4. Reliability
5. Maintainability
6. Usability
7. Testability
8. Concurrency safety

---

# 31. Non-Functional Requirements Acceptance Criteria

The system shall be considered compliant with the major non-functional requirements when:

### Performance

Normal operations provide acceptable response times under the defined project environment.

### Security

Authentication is enforced, passwords are protected, and user input is handled using parameterized database queries.

### Reliability

Banking operations maintain correct account balances and failed transactions do not leave invalid partial state.

### Data Integrity

Primary keys, foreign keys, unique constraints, and appropriate database constraints maintain valid data relationships.

### Usability

Employees can navigate the application and understand input validation, success messages, and error messages.

### Maintainability

The implementation maintains clear separation between UI, business logic, and database access.

### Concurrency

Concurrent transaction tests do not produce incorrect account balances.

### Testability

Core business logic and database operations can be tested independently.

---

# 32. Constraints Affecting NFRs

The following constraints influence the non-functional requirements:

1. The application is a desktop Java Swing application.
2. The system is intended primarily for academic use.
3. The system uses a local or controlled MySQL environment.
4. The expected dataset is relatively small compared with production banking systems.
5. Java 17 is the required runtime.
6. MySQL 8 is the required database.
7. JDBC is the required database connectivity mechanism.
8. IntelliJ IDEA is the primary development environment.
9. The system does not process real-world financial transactions.

---

# 33. Future NFR Considerations

If the system were extended into a production-level application, additional requirements would need to be considered, including:

* High availability
* Disaster recovery
* Horizontal scalability
* Advanced authentication
* Multi-factor authentication
* Encryption at rest
* Encryption in transit
* Centralized monitoring
* Distributed logging
* Automated backups
* High-volume transaction processing
* Load balancing
* Database replication
* Comprehensive audit controls
* Regulatory compliance

These requirements are outside the current academic scope.

---

# 34. Conclusion

This document defines the non-functional quality requirements for the Banking Management System.

The requirements establish expectations for:

* Performance
* Security
* Reliability
* Availability
* Usability
* Maintainability
* Scalability
* Portability
* Compatibility
* Data integrity
* Concurrency
* Testability
* Recoverability
* Logging
* Documentation

Together with the Software Requirements Specification, Requirement Analysis, and Functional Requirements, this document establishes a requirements baseline for the system.

The next phase will translate these requirements into detailed business rules and use cases.

The development process will continue with:

```text
SRS
 ↓
Requirement Analysis
 ↓
Functional Requirements
 ↓
Non-Functional Requirements
 ↓
Business Rules
 ↓
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
 ↓
Final Documentation
```

---

# Document Status

**Status:** Completed — Non-Functional Requirements Draft
**Version:** 1.0
**Previous Document:** `docs/requirements/functional-requirements.md`
**Next Document:** `docs/requirements/business-rules.md`

---

**End of Non-Functional Requirements**
