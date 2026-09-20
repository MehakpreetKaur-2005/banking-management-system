\# Software Requirements Specification (SRS)



\# Banking Management System Using Java



\*\*Document Type:\*\* Software Requirements Specification (SRS)

\*\*Standard Alignment:\*\* IEEE 29148 Requirements Engineering Guidelines

\*\*Project Type:\*\* University Software Engineering Project

\*\*Application Type:\*\* Desktop Banking Management System

\*\*Version:\*\* 1.0

\*\*Technology:\*\* Java 17, Java Swing, JDBC, MySQL 8

\*\*IDE:\*\* IntelliJ IDEA

\*\*Database:\*\* MySQL 8



\---



\# Document Control



| Version | Date           | Description | Author       |

| ------- | -------------- | ----------- | ------------ |

| 1.0     | September 2026 | Initial SRS | Project Team |



\---



\# Table of Contents



1\. \[Introduction](#1-introduction)

2\. \[Overall Description](#2-overall-description)

3\. \[Stakeholders and User Classes](#3-stakeholders-and-user-classes)

4\. \[Product Perspective](#4-product-perspective)

5\. \[Product Functions](#5-product-functions)

6\. \[System Features](#6-system-features)

7\. \[Functional Requirements](#7-functional-requirements)

8\. \[Non-Functional Requirements](#8-non-functional-requirements)

9\. \[Business Rules](#9-business-rules)

10\. \[Data Requirements](#10-data-requirements)

11\. \[External Interface Requirements](#11-external-interface-requirements)

12\. \[System Architecture](#12-system-architecture)

13\. \[Use Case Specifications](#13-use-case-specifications)

14\. \[Object-Oriented Requirements](#14-object-oriented-requirements)

15\. \[Database Requirements](#15-database-requirements)

16\. \[Data Integrity Requirements](#16-data-integrity-requirements)

17\. \[Transaction and Concurrency Requirements](#17-transaction-and-concurrency-requirements)

18\. \[Exception Handling Requirements](#18-exception-handling-requirements)

19\. \[Security Requirements](#19-security-requirements)

20\. \[Logging and Audit Requirements](#20-logging-and-audit-requirements)

21\. \[UI Requirements](#21-ui-requirements)

22\. \[Detailed Validation Requirements](#22-detailed-validation-requirements)

23\. \[Error Message Requirements](#23-error-message-requirements)

24\. \[Assumptions](#24-assumptions)

25\. \[Dependencies](#25-dependencies)

26\. \[Constraints](#26-constraints)

27\. \[Out of Scope](#27-out-of-scope)

28\. \[Acceptance Criteria](#28-acceptance-criteria)

29\. \[Requirement Prioritization](#29-requirement-prioritization)

30\. \[Traceability](#30-traceability)

31\. \[System-Level Use Case List](#31-system-level-use-case-list)

32\. \[System Behaviour Overview](#32-system-behaviour-overview)

33\. \[Quality Attributes](#33-quality-attributes)

34\. \[Design Principles](#34-design-principles)

35\. \[Future Enhancements](#35-future-enhancements)

36\. \[Glossary](#36-glossary)

37\. \[Appendix A — Requirement ID Convention](#37-appendix-a--requirement-id-convention)

38\. \[Appendix B — Proposed Domain Model](#38-appendix-b--proposed-domain-model)

39\. \[Appendix C — Requirements-to-Implementation Mapping](#39-appendix-c--requirements-to-implementation-mapping)

40\. \[Appendix D — Development Sequence](#40-appendix-d--development-sequence)

41\. \[Final System Requirement Summary](#41-final-system-requirement-summary)

42\. \[SRS Approval](#42-srs-approval)



\---



\# 1. Introduction



\## 1.1 Purpose



This Software Requirements Specification defines the functional and non-functional requirements for the \*\*Banking Management System Using Java\*\*.



The purpose of the system is to provide a desktop-based banking management application that enables authorized bank employees to manage customer information, bank accounts, deposits, withdrawals, fund transfers, and transaction records through a graphical user interface.



The system will be developed using:



\* Java 17

\* Java Swing

\* JDBC

\* MySQL 8

\* IntelliJ IDEA



The system will demonstrate important object-oriented programming and software engineering concepts including:



\* Encapsulation

\* Abstraction

\* Inheritance

\* Interfaces

\* Polymorphism

\* Method overloading

\* Method overriding

\* Collections

\* Exception handling

\* Multithreading

\* Object class methods

\* Database connectivity

\* SQL transactions

\* Layered architecture



This document establishes the requirements that will guide subsequent design, implementation, testing, and documentation activities.



\---



\## 1.2 Scope



The Banking Management System is intended for use by \*\*bank employees\*\* to perform routine customer and account management activities.



The system will provide functionality for:



1\. Employee authentication

2\. Customer registration

3\. Customer search

4\. Customer information update

5\. Savings account creation

6\. Current account creation

7\. Account search

8\. Balance enquiry

9\. Deposit processing

10\. Withdrawal processing

11\. Fund transfer

12\. Transaction history

13\. Dashboard statistics

14\. Account closure

15\. Interest calculation where applicable

16\. Transaction status management

17\. Error and exception handling

18\. Concurrent transaction demonstration

19\. Database persistence



> \*\*Note:\*\* The application is intended as an academic banking simulation and is not intended to operate as a production banking platform or process real financial transactions.



\---



\## 1.3 Intended Audience



This document is intended for:



\* University project evaluators

\* Project supervisors

\* Software developers

\* Software testers

\* Database designers

\* System designers

\* Project team members

\* Future maintainers



The SRS will serve as the primary reference document during the development lifecycle.



\---



\## 1.4 Product Objectives



The primary objectives of the Banking Management System are:



\### Objective 1 — Customer Management



Provide employees with the ability to create, search, and update customer records.



\### Objective 2 — Account Management



Allow employees to open and manage different types of bank accounts.



\### Objective 3 — Banking Operations



Provide secure and reliable deposit, withdrawal, and fund transfer functionality.



\### Objective 4 — Transaction Management



Maintain a persistent record of banking transactions and their status.



\### Objective 5 — Data Integrity



Ensure that customer, account, and transaction information remains consistent.



\### Objective 6 — Object-Oriented Design



Demonstrate practical application of Java OOP principles.



\### Objective 7 — Database Integration



Demonstrate interaction between a Java application and MySQL using JDBC.



\### Objective 8 — Concurrency



Demonstrate safe handling of concurrent banking operations through multithreading and database transaction control.



\---



\# 2. Overall Description



\## 2.1 Product Perspective



The Banking Management System will be a standalone desktop application.



The overall system will follow a three-tier architecture:



```text

+------------------------------------------------+

|              Presentation Layer                |

|                 Java Swing                    |

|                                                |

| Login | Dashboard | Customer | Account | Txn  |

+-------------------------+----------------------+

&#x20;                         |

&#x20;                         v

+------------------------------------------------+

|               Business Layer                   |

|                                                |

| Services | Business Rules | Validation | OOP   |

+-------------------------+----------------------+

&#x20;                         |

&#x20;                         v

+------------------------------------------------+

|                  Data Layer                    |

|                                                |

| DAO | JDBC | SQL | Transactions                |

+-------------------------+----------------------+

&#x20;                         |

&#x20;                         v

+------------------------------------------------+

|                  MySQL 8                      |

|                                                |

| Employees | Customers | Accounts | Transactions|

+------------------------------------------------+

```



The presentation layer will not directly access the database.



All business operations will pass through the service layer.



The DAO layer will be responsible for database communication.



\---



\## 2.2 Major System Components



The system will contain the following major components:



\### Authentication Component



Responsible for employee login and authentication.



\### Customer Management Component



Responsible for customer creation, searching, and updating.



\### Account Management Component



Responsible for opening, viewing, updating status, and closing accounts.



\### Transaction Component



Responsible for deposits, withdrawals, and transfers.



\### Reporting Component



Responsible for transaction history and dashboard statistics.



\### Database Component



Responsible for persistent storage through MySQL and JDBC.



\### Exception Management Component



Responsible for handling expected and unexpected application errors.



\---



\# 3. Stakeholders and User Classes



\## 3.1 Stakeholders



| Stakeholder            | Interest                                            |

| ---------------------- | --------------------------------------------------- |

| Bank Employee          | Performs daily banking operations                   |

| Bank Manager           | Reviews customer/account information and statistics |

| System Administrator   | Maintains system configuration                      |

| Project Supervisor     | Evaluates project implementation                    |

| Developer              | Designs and implements the system                   |

| Tester                 | Verifies system correctness                         |

| Database Administrator | Maintains database structure and integrity          |

| University Evaluator   | Evaluates academic requirements                     |



\---



\## 3.2 Primary User



\### Bank Employee



The primary user is an authorized bank employee.



The employee can:



\* Log into the system

\* Register customers

\* Search customers

\* Update customer information

\* Open accounts

\* Perform deposits

\* Perform withdrawals

\* Transfer funds

\* View balances

\* View transaction history

\* Close accounts where permitted



\---



\## 3.3 Secondary User



\### Bank Manager



A manager may have access to:



\* Dashboard statistics

\* Customer information

\* Account information

\* Transaction history

\* Operational reports



Manager functionality may be implemented as an extension in a future version.



\---



\# 4. Product Perspective



The system is a standalone desktop banking management application designed around a layered software architecture.



The system will interact with:



```text

Employee

&#x20;  |

&#x20;  v

Java Swing Application

&#x20;  |

&#x20;  v

Business Services

&#x20;  |

&#x20;  v

DAO / JDBC

&#x20;  |

&#x20;  v

MySQL Database

```



The architecture is intended to provide separation of concerns, maintainability, testability, and extensibility.



\---



\# 5. Product Functions



The major functions of the system are:



| Function              | Description                     |

| --------------------- | ------------------------------- |

| Employee Login        | Authenticate employees          |

| Customer Registration | Create new customer             |

| Customer Search       | Search customer records         |

| Customer Update       | Modify customer details         |

| Account Opening       | Create savings/current account  |

| Account Search        | Retrieve account information    |

| Deposit               | Add funds                       |

| Withdrawal            | Remove funds                    |

| Transfer              | Transfer funds between accounts |

| Balance Enquiry       | Display account balance         |

| Transaction History   | Display transaction records     |

| Dashboard             | Display operational statistics  |

| Account Closure       | Close eligible accounts         |

| Interest Calculation  | Calculate savings interest      |

| Transaction Logging   | Record transaction details      |



\---



\# 6. System Features



\## 6.1 Employee Authentication



The system shall require employees to authenticate before accessing banking functionality.



\### Description



An employee shall provide valid login credentials.



The system shall validate the credentials against the employee database.



If authentication succeeds, the system shall display the employee dashboard.



If authentication fails, the system shall display an appropriate error message.



\---



\## 6.2 Customer Registration



Authorized employees shall be able to register new customers.



Required information may include:



\* Customer ID

\* Full name

\* Date of birth

\* Gender

\* Phone number

\* Email

\* Address

\* Government identification reference

\* Date of registration



The system shall ensure that the customer identifier is unique.



\---



\## 6.3 Customer Search



Employees shall be able to search for customers.



Search criteria may include:



\* Customer ID

\* Name

\* Phone number

\* Email



The system shall display matching customer information.



\---



\## 6.4 Customer Update



Authorized employees shall be able to update permitted customer information.



The system shall:



1\. Locate the customer.

2\. Display existing information.

3\. Allow modification of permitted fields.

4\. Validate updated information.

5\. Save the changes.

6\. Display confirmation.



\---



\## 6.5 Savings Account



The system shall support savings accounts.



A savings account shall maintain a minimum balance of:



\*\*₹1,000\*\*



A withdrawal shall be rejected if it causes the account balance to fall below the minimum required balance.



The minimum balance requirement shall be enforced by the business layer.



\---



\## 6.6 Current Account



The system shall support current accounts.



A current account shall allow an overdraft up to:



\*\*₹5,000\*\*



Therefore, the lowest permissible balance for a current account shall be:



\*\*-₹5,000\*\*



Any withdrawal or transfer that exceeds the permitted overdraft shall be rejected.



\---



\## 6.7 Deposit



An employee shall be able to deposit money into an active account.



Rules:



\* Deposit amount must be greater than zero.

\* Account must exist.

\* Account must be active.

\* Balance shall increase by the deposited amount.

\* A transaction record shall be created.



\---



\## 6.8 Withdrawal



An employee shall be able to withdraw money from an active account.



Rules:



\* Withdrawal amount must be greater than zero.

\* Account must exist.

\* Account must be active.

\* Savings account minimum balance must be respected.

\* Current account overdraft limit must be respected.

\* A transaction record shall be created.



\---



\## 6.9 Fund Transfer



The system shall support transfers between eligible accounts.



A transfer shall contain:



\* Source account

\* Destination account

\* Amount

\* Transaction ID

\* Timestamp

\* Transaction status



The operation shall be atomic.



The system shall ensure that either:



1\. Both debit and credit operations succeed, or

2\. Neither operation is permanently applied.



This shall be implemented using JDBC/SQL transactions.



\---



\## 6.10 Balance Enquiry



Employees shall be able to retrieve the current balance of an account.



The system shall display:



\* Account number

\* Account type

\* Customer name

\* Current balance

\* Account status



\---



\## 6.11 Transaction History



Employees shall be able to view transaction history.



Transaction information shall include:



\* Transaction ID

\* Account number

\* Transaction type

\* Amount

\* Date/time

\* Status

\* Reference information



Transactions may include:



\* Deposit

\* Withdrawal

\* Transfer

\* Interest

\* Account-related transactions



\---



\## 6.12 Dashboard Statistics



The dashboard shall provide summary information such as:



\* Total customers

\* Total accounts

\* Savings accounts

\* Current accounts

\* Active accounts

\* Total transaction count

\* Total deposits

\* Total withdrawals



Dashboard statistics shall be retrieved from the database.



\---



\## 6.13 Account Closure



The system may support account closure.



Before closure, the system shall verify:



\* Account exists.

\* Account is active.

\* Account is eligible for closure.

\* No unresolved transaction exists.

\* Balance requirements are satisfied.



The exact closure policy shall be finalized during detailed design.



\---



\## 6.14 Interest Calculation



Interest calculation is an optional feature.



If implemented, the system shall calculate interest according to a configurable interest rate.



The calculation shall be implemented in the business layer rather than directly in the Swing UI.



\---



\# 7. Functional Requirements



Functional requirements define what the system shall do.



\## 7.1 Authentication Requirements



| ID          | Requirement                                                                         |

| ----------- | ----------------------------------------------------------------------------------- |

| FR-AUTH-001 | The system shall provide an employee login screen.                                  |

| FR-AUTH-002 | The login screen shall accept employee credentials.                                 |

| FR-AUTH-003 | The system shall validate employee credentials against the database.                |

| FR-AUTH-004 | The system shall prevent unauthorized users from accessing protected functionality. |

| FR-AUTH-005 | The system shall display an appropriate error message when authentication fails.    |

| FR-AUTH-006 | The system shall provide a logout function.                                         |



\---



\## 7.2 Customer Requirements



| ID          | Requirement                                                                   |

| ----------- | ----------------------------------------------------------------------------- |

| FR-CUST-001 | The system shall allow an authorized employee to register a customer.         |

| FR-CUST-002 | The system shall generate or validate a unique customer ID.                   |

| FR-CUST-003 | The system shall validate mandatory customer information.                     |

| FR-CUST-004 | The system shall prevent duplicate customer IDs.                              |

| FR-CUST-005 | The system shall allow employees to search customers.                         |

| FR-CUST-006 | The system shall allow employees to update permitted customer information.    |

| FR-CUST-007 | The system shall display an error when the requested customer does not exist. |



\---



\## 7.3 Account Requirements



| ID         | Requirement                                                                                    |

| ---------- | ---------------------------------------------------------------------------------------------- |

| FR-ACC-001 | The system shall allow an authorized employee to open a bank account for an existing customer. |

| FR-ACC-002 | The system shall support Savings Account creation.                                             |

| FR-ACC-003 | The system shall support Current Account creation.                                             |

| FR-ACC-004 | The system shall generate a unique account number.                                             |

| FR-ACC-005 | The system shall associate each account with exactly one customer.                             |

| FR-ACC-006 | The system shall allow one customer to own multiple accounts.                                  |

| FR-ACC-007 | The system shall store account status.                                                         |

| FR-ACC-008 | The system shall allow authorized users to view account information.                           |

| FR-ACC-009 | The system shall prevent transactions against closed accounts.                                 |



\---



\## 7.4 Deposit Requirements



| ID         | Requirement                                                                         |

| ---------- | ----------------------------------------------------------------------------------- |

| FR-DEP-001 | The system shall allow deposits into active accounts.                               |

| FR-DEP-002 | The system shall reject a deposit amount less than or equal to zero.                |

| FR-DEP-003 | The system shall update the account balance after a successful deposit.             |

| FR-DEP-004 | The system shall generate a unique transaction ID.                                  |

| FR-DEP-005 | The system shall record the deposit transaction.                                    |

| FR-DEP-006 | The system shall report transaction failure when the operation cannot be completed. |



\---



\## 7.5 Withdrawal Requirements



| ID        | Requirement                                                                                   |

| --------- | --------------------------------------------------------------------------------------------- |

| FR-WD-001 | The system shall allow withdrawals from active accounts.                                      |

| FR-WD-002 | The system shall reject a withdrawal amount less than or equal to zero.                       |

| FR-WD-003 | The system shall enforce savings account minimum balance requirements.                        |

| FR-WD-004 | The system shall enforce the current account overdraft limit.                                 |

| FR-WD-005 | The system shall update the account balance after a successful withdrawal.                    |

| FR-WD-006 | The system shall record successful withdrawals.                                               |

| FR-WD-007 | The system shall record failed withdrawal attempts with an appropriate status where required. |



\---



\## 7.6 Transfer Requirements



| ID         | Requirement                                                              |

| ---------- | ------------------------------------------------------------------------ |

| FR-TRF-001 | The system shall allow transfers between eligible accounts.              |

| FR-TRF-002 | The source account shall be debited by the transfer amount.              |

| FR-TRF-003 | The destination account shall be credited by the transfer amount.        |

| FR-TRF-004 | The source and destination accounts shall not be identical.              |

| FR-TRF-005 | The transfer amount shall be greater than zero.                          |

| FR-TRF-006 | The source account shall satisfy applicable balance restrictions.        |

| FR-TRF-007 | The transfer shall execute as a single database transaction.             |

| FR-TRF-008 | The system shall roll back the transfer if any required operation fails. |

| FR-TRF-009 | The system shall record the transaction status.                          |

| FR-TRF-010 | The system shall generate a unique transaction identifier.               |



\---



\## 7.7 Transaction History Requirements



| ID         | Requirement                                                                  |

| ---------- | ---------------------------------------------------------------------------- |

| FR-TXN-001 | The system shall store transaction records.                                  |

| FR-TXN-002 | The system shall allow authorized employees to retrieve transaction history. |

| FR-TXN-003 | The system shall display transaction type.                                   |

| FR-TXN-004 | The system shall display transaction amount.                                 |

| FR-TXN-005 | The system shall display transaction timestamp.                              |

| FR-TXN-006 | The system shall display transaction status.                                 |

| FR-TXN-007 | The system shall support searching/filtering transaction history.            |



\---



\## 7.8 Dashboard Requirements



| ID          | Requirement                                                  |

| ----------- | ------------------------------------------------------------ |

| FR-DASH-001 | The system shall display the number of registered customers. |

| FR-DASH-002 | The system shall display the number of accounts.             |

| FR-DASH-003 | The system shall display account type statistics.            |

| FR-DASH-004 | The system shall display transaction statistics.             |

| FR-DASH-005 | The dashboard shall retrieve statistics from the database.   |



\---



\# 8. Non-Functional Requirements



\## 8.1 Performance



| ID           | Requirement                                                                                                         |

| ------------ | ------------------------------------------------------------------------------------------------------------------- |

| NFR-PERF-001 | Normal database operations should complete within an acceptable response time under the expected academic workload. |

| NFR-PERF-002 | The UI shall remain responsive during normal operations.                                                            |

| NFR-PERF-003 | Database connections shall be properly closed or returned to the connection management mechanism.                   |

| NFR-PERF-004 | Long-running operations should not unnecessarily block the Swing Event Dispatch Thread.                             |



\---



\## 8.2 Reliability



| ID          | Requirement                                                                                               |

| ----------- | --------------------------------------------------------------------------------------------------------- |

| NFR-REL-001 | The system shall preserve database consistency after successful transactions.                             |

| NFR-REL-002 | Failed transfers shall not leave the source account debited without the corresponding destination credit. |

| NFR-REL-003 | Unexpected application errors shall be handled without terminating the application unnecessarily.         |



\---



\## 8.3 Security



| ID          | Requirement                                                                          |

| ----------- | ------------------------------------------------------------------------------------ |

| NFR-SEC-001 | Only authenticated employees shall access protected functions.                       |

| NFR-SEC-002 | Passwords shall not be stored as plain text in a production-oriented implementation. |

| NFR-SEC-003 | SQL statements shall use parameterized queries/prepared statements.                  |

| NFR-SEC-004 | The application shall validate user input before performing database operations.     |

| NFR-SEC-005 | Users shall not be allowed to directly manipulate SQL statements through the GUI.    |



\---



\## 8.4 Maintainability



| ID           | Requirement                                                    |

| ------------ | -------------------------------------------------------------- |

| NFR-MAIN-001 | The system shall follow a layered architecture.                |

| NFR-MAIN-002 | Presentation logic shall be separated from business logic.     |

| NFR-MAIN-003 | Database access shall be isolated within DAO classes.          |

| NFR-MAIN-004 | Business rules shall primarily reside in service/domain logic. |

| NFR-MAIN-005 | Classes shall have clearly defined responsibilities.           |



\---



\## 8.5 Usability



| ID          | Requirement                                                          |

| ----------- | -------------------------------------------------------------------- |

| NFR-USE-001 | The system shall provide a graphical user interface.                 |

| NFR-USE-002 | Input fields shall have meaningful labels.                           |

| NFR-USE-003 | Validation errors shall be understandable to users.                  |

| NFR-USE-004 | Destructive operations shall require confirmation where appropriate. |

| NFR-USE-005 | Navigation between major modules shall be straightforward.           |



\---



\## 8.6 Portability



| ID           | Requirement                                                                                                      |

| ------------ | ---------------------------------------------------------------------------------------------------------------- |

| NFR-PORT-001 | The application shall be developed using Java 17.                                                                |

| NFR-PORT-002 | The system should be executable on operating systems supporting the selected Java runtime and MySQL environment. |



\---



\## 8.7 Scalability



The academic version is designed for a relatively small dataset.



However, the architecture shall allow additional functionality to be introduced without redesigning the entire application.



\---



\# 9. Business Rules



\## BR-001 — Unique Customer



Each customer shall have a unique customer ID.



\## BR-002 — Multiple Accounts



A customer may own multiple bank accounts.



\## BR-003 — Unique Account Number



Each account shall have a unique account number.



\## BR-004 — Savings Minimum Balance



A Savings Account shall maintain a minimum balance of:



\*\*₹1,000\*\*



A withdrawal shall not be permitted if it causes the balance to fall below ₹1,000.



\## BR-005 — Current Account Overdraft



A Current Account may have a maximum overdraft of:



\*\*₹5,000\*\*



The balance therefore cannot fall below:



\*\*-₹5,000\*\*



\## BR-006 — Positive Transaction Amount



Deposit, withdrawal, and transfer amounts shall be greater than zero.



\## BR-007 — Active Account



Deposits, withdrawals, and transfers shall only be permitted for active accounts.



\## BR-008 — Valid Source Account



A transfer shall require a valid source account.



\## BR-009 — Valid Destination Account



A transfer shall require a valid destination account.



\## BR-010 — Different Transfer Accounts



Source and destination accounts shall be different.



\## BR-011 — Atomic Transfer



A transfer shall be treated as one atomic operation.



\## BR-012 — Unique Transaction ID



Every transaction shall have a unique transaction identifier.



\## BR-013 — Failed Transactions



Failed transactions may be recorded with an appropriate failure status for auditability.



\## BR-014 — Customer Ownership



An account must belong to an existing customer.



\## BR-015 — Closed Account



Closed accounts shall not accept normal banking transactions.



\---



\# 10. Data Requirements



The system shall maintain persistent information about:



\## Employee



\* Employee ID

\* Name

\* Username

\* Password/hash

\* Role

\* Status



\## Customer



\* Customer ID

\* Name

\* Date of birth

\* Gender

\* Phone

\* Email

\* Address

\* Registration date

\* Status



\## Account



\* Account number

\* Customer ID

\* Account type

\* Balance

\* Status

\* Opening date



\## Transaction



\* Transaction ID

\* Source account

\* Destination account where applicable

\* Transaction type

\* Amount

\* Status

\* Timestamp

\* Description/reference



\---



\# 11. External Interface Requirements



\## 11.1 User Interface



The application shall use Java Swing.



The interface shall contain screens such as:



1\. Login

2\. Dashboard

3\. Customer Registration

4\. Customer Search

5\. Customer Update

6\. Account Opening

7\. Account Search

8\. Deposit

9\. Withdrawal

10\. Fund Transfer

11\. Balance Enquiry

12\. Transaction History

13\. Account Closure



\---



\## 11.2 Database Interface



The application shall communicate with MySQL 8 using JDBC.



The data layer shall use:



\* JDBC Connection

\* PreparedStatement

\* ResultSet

\* SQL transactions

\* Commit

\* Rollback



\---



\## 11.3 Hardware Interface



No specialized hardware is required.



The system shall operate on a standard computer capable of running Java 17, IntelliJ IDEA, and MySQL.



\---



\## 11.4 Software Interface



Required software components include:



\* Java Development Kit 17

\* MySQL 8

\* MySQL JDBC Driver

\* IntelliJ IDEA

\* Operating system supporting Java 17



\---



\# 12. System Architecture



The system shall follow a three-tier architecture.



\## 12.1 Presentation Layer



Package:



```text

ui

```



Responsibilities:



\* Display Swing forms

\* Receive user input

\* Display results

\* Display validation errors

\* Invoke service methods



The UI shall not directly execute SQL queries.



\---



\## 12.2 Business Layer



Packages:



```text

service

model

exception

```



Responsibilities:



\* Business rules

\* Validation

\* Banking operations

\* Account-specific behavior

\* Transaction processing

\* Exception handling



Examples:



```text

CustomerService

AccountService

TransactionService

AuthenticationService

DashboardService

```



\---



\## 12.3 Data Access Layer



Packages:



```text

dao

util

```



Responsibilities:



\* Database connection

\* SQL queries

\* CRUD operations

\* ResultSet mapping

\* Transaction control



Examples:



```text

CustomerDAO

AccountDAO

TransactionDAO

EmployeeDAO

```



\---



\# 13. Use Case Specifications



\## UC-01: Employee Login



\*\*Actor:\*\* Employee



\*\*Precondition:\*\* Employee account exists.



\*\*Trigger:\*\* Employee opens the application.



\### Main Flow



1\. System displays login screen.

2\. Employee enters username.

3\. Employee enters password.

4\. Employee selects Login.

5\. System validates credentials.

6\. System authenticates employee.

7\. System displays dashboard.



\### Alternative Flow



If credentials are invalid:



1\. System rejects authentication.

2\. System displays an error message.

3\. Employee may retry.



\### Postcondition



Authenticated employee has access to permitted functionality.



\---



\## UC-02: Register Customer



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee opens customer registration.

2\. System displays registration form.

3\. Employee enters customer information.

4\. Employee submits form.

5\. System validates input.

6\. System checks customer uniqueness.

7\. System stores customer.

8\. System displays confirmation.



\### Exceptions



\* Missing mandatory fields

\* Invalid phone number

\* Invalid email

\* Duplicate customer ID

\* Database failure



\---



\## UC-03: Search Customer



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee opens customer search.

2\. Employee selects search criterion.

3\. Employee enters search value.

4\. System searches database.

5\. System displays matching customer records.



\### Exception



If no matching customer exists, the system displays an appropriate message.



\---



\## UC-04: Update Customer



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee searches for customer.

2\. System displays customer information.

3\. Employee modifies permitted information.

4\. System validates changes.

5\. System updates database.

6\. System confirms successful update.



\---



\## UC-05: Open Savings Account



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee selects account opening.

2\. Employee identifies customer.

3\. Employee selects Savings Account.

4\. System validates customer.

5\. System creates unique account number.

6\. System creates account.

7\. System displays account information.



\---



\## UC-06: Open Current Account



The flow is similar to Savings Account creation but creates an account with type `CURRENT`.



The applicable overdraft business rule shall be associated with the account type.



\---



\## UC-07: Deposit Money



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee enters account number.

2\. System retrieves account.

3\. Employee enters deposit amount.

4\. System validates amount.

5\. System verifies account status.

6\. System updates balance.

7\. System creates transaction record.

8\. System displays success message.



\---



\## UC-08: Withdraw Money



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee enters account number.

2\. System retrieves account.

3\. Employee enters withdrawal amount.

4\. System validates amount.

5\. System checks account status.

6\. System checks account balance rules.

7\. System updates balance.

8\. System records transaction.

9\. System displays confirmation.



\### Alternative Flow



If the withdrawal violates account restrictions:



1\. System rejects transaction.

2\. Account balance remains unchanged.

3\. Appropriate transaction status may be recorded.



\---



\## UC-09: Transfer Funds



\*\*Actor:\*\* Employee



\### Main Flow



1\. Employee enters source account.

2\. Employee enters destination account.

3\. Employee enters amount.

4\. System validates both accounts.

5\. System validates amount.

6\. System verifies account status.

7\. System verifies source account balance restrictions.

8\. System begins database transaction.

9\. System debits source account.

10\. System credits destination account.

11\. System records transaction.

12\. System commits transaction.

13\. System displays success.



\### Failure Flow



If any operation fails:



1\. System rolls back the database transaction.

2\. Source balance remains unchanged.

3\. Destination balance remains unchanged.

4\. Transaction status is marked appropriately.

5\. User receives an error message.



\---



\## UC-10: Balance Enquiry



\*\*Actor:\*\* Employee



1\. Employee enters account number.

2\. System retrieves account.

3\. System displays current balance and account details.



\---



\## UC-11: View Transaction History



\*\*Actor:\*\* Employee



1\. Employee selects transaction history.

2\. Employee enters search criteria.

3\. System retrieves transactions.

4\. System displays transaction records.



\---



\## UC-12: Close Account



\*\*Actor:\*\* Employee



1\. Employee selects account.

2\. System retrieves account.

3\. System verifies eligibility.

4\. Employee confirms closure.

5\. System changes account status.

6\. System records account closure event.



\---



\# 14. Object-Oriented Requirements



The project shall explicitly demonstrate object-oriented programming principles.



\## 14.1 Encapsulation



Classes shall encapsulate their internal state.



Example:



```text

Account

\- accountNumber

\- balance

\- status

```



Fields should generally be private and accessed through controlled methods.



\---



\## 14.2 Abstraction



The system shall use abstract classes where appropriate.



Example:



```text

Account

&#x20;   |

&#x20;   +---- SavingsAccount

&#x20;   |

&#x20;   +---- CurrentAccount

```



The abstract `Account` class can define common account behavior.



\---



\## 14.3 Inheritance



`SavingsAccount` and `CurrentAccount` shall inherit common properties and behavior from `Account`.



\---



\## 14.4 Interfaces



The system shall demonstrate interfaces for behavior that may be shared by multiple classes.



Possible example:



```text

InterestCalculable

```



with a method such as:



```text

calculateInterest()

```



\---



\## 14.5 Polymorphism



The system shall support polymorphic behavior.



For example, an `Account` reference may refer to either:



```text

SavingsAccount

```



or:



```text

CurrentAccount

```



Account-specific behavior can then be resolved through method overriding.



\---



\## 14.6 Method Overloading



The system shall demonstrate method overloading where meaningful.



Example:



```text

searchCustomer(int customerId)



searchCustomer(String name)



searchCustomer(String name, String phone)

```



Overloading shall be used only where it improves API clarity.



\---



\## 14.7 Method Overriding



Subclasses shall override appropriate methods from parent classes.



Examples include:



```text

calculateInterest()

toString()

```



\---



\## 14.8 Object Class Methods



Relevant domain classes shall demonstrate:



\### `toString()`



Used for readable object representation.



\### `equals()`



Used for logical object comparison.



\### `hashCode()`



Implemented consistently with `equals()`.



For example, account equality may be based on the unique account number.



\---



\# 15. Database Requirements



The system shall use MySQL 8 as its relational database management system.



The database shall contain at least the following tables:



```text

employees

customers

accounts

transactions

```



\---



\## 15.1 Employees Table



Conceptual attributes:



```text

employee\_id

name

username

password\_hash

role

status

created\_at

```



Primary Key:



```text

employee\_id

```



Unique constraint:



```text

username

```



\---



\## 15.2 Customers Table



Conceptual attributes:



```text

customer\_id

full\_name

date\_of\_birth

gender

phone

email

address

created\_at

status

```



Primary Key:



```text

customer\_id

```



\---



\## 15.3 Accounts Table



Conceptual attributes:



```text

account\_number

customer\_id

account\_type

balance

status

opened\_at

closed\_at

```



Primary Key:



```text

account\_number

```



Foreign Key:



```text

customer\_id → customers.customer\_id

```



\---



\## 15.4 Transactions Table



Conceptual attributes:



```text

transaction\_id

source\_account

destination\_account

transaction\_type

amount

status

transaction\_time

description

```



Primary Key:



```text

transaction\_id

```



Foreign Keys:



```text

source\_account → accounts.account\_number



destination\_account → accounts.account\_number

```



The final schema will be established during the database design phase.



\---



\# 16. Data Integrity Requirements



The database shall enforce appropriate constraints.



These may include:



\* Primary keys

\* Foreign keys

\* Unique constraints

\* NOT NULL constraints

\* CHECK constraints where supported and appropriate

\* Indexes

\* Referential integrity



Business rules shall not rely solely on GUI validation.



Important rules shall also be enforced within the business/service layer and, where appropriate, through database constraints.



\---



\# 17. Transaction and Concurrency Requirements



\## 17.1 SQL Transactions



Fund transfers shall use database transactions.



Conceptually:



```text

BEGIN TRANSACTION



Debit source account



Credit destination account



Create transaction record



COMMIT

```



If an operation fails:



```text

ROLLBACK

```



\---



\## 17.2 Atomicity



A fund transfer shall satisfy the atomicity property.



The system shall not permanently perform only one side of the transfer.



\---



\## 17.3 Consistency



The system shall preserve account and transaction data consistency.



\---



\## 17.4 Isolation



Concurrent transactions shall be handled using appropriate database transaction isolation and/or row-level locking mechanisms.



The exact JDBC implementation shall be determined during implementation.



\---



\## 17.5 Durability



Once a transaction is committed, its changes shall be persisted in MySQL.



\---



\## 17.6 Multithreading



The project shall demonstrate Java multithreading.



A possible demonstration is concurrent transactions against accounts.



Example:



```text

Thread 1 → Deposit ₹2,000



Thread 2 → Withdraw ₹1,000

```



The implementation shall demonstrate that concurrent operations do not incorrectly overwrite account balances.



Multithreading shall not be used merely for demonstration if it compromises application correctness.



\---



\# 18. Exception Handling Requirements



The system shall use Java exception handling to handle expected failures.



Potential custom exceptions include:



```text

BankingException

CustomerNotFoundException

AccountNotFoundException

InsufficientBalanceException

InvalidAmountException

AccountClosedException

AuthenticationException

TransactionFailedException

```



\---



\## 18.1 Invalid Amount



If the user enters an amount less than or equal to zero, the system shall reject the operation.



\---



\## 18.2 Customer Not Found



If a requested customer does not exist, the service layer shall report an appropriate exception.



\---



\## 18.3 Account Not Found



If an account number does not exist, the system shall reject the operation.



\---



\## 18.4 Insufficient Balance



If a withdrawal or transfer violates account balance rules, the operation shall fail.



\---



\## 18.5 Database Failure



Database exceptions shall be handled appropriately.



The UI shall not expose raw database stack traces to users.



\---



\# 19. Security Requirements



Although this is an academic project, the design shall follow basic secure development practices.



\## 19.1 Authentication



Protected functionality shall require authentication.



\## 19.2 Authorization



If roles are implemented, functionality shall be restricted according to employee permissions.



\## 19.3 Password Protection



Passwords should be stored using secure hashing rather than plain text.



\## 19.4 SQL Injection Prevention



Prepared statements shall be used.



Example:



```text

PreparedStatement

```



rather than constructing SQL using string concatenation.



\## 19.5 Input Validation



All user-controlled values shall be validated before processing.



\---



\# 20. Logging and Audit Requirements



The system should maintain useful application logs for development and debugging.



Important events may include:



\* Login success

\* Login failure

\* Customer creation

\* Customer update

\* Account creation

\* Deposit

\* Withdrawal

\* Transfer

\* Account closure

\* Transaction failure

\* Database errors



Sensitive information such as passwords shall never be written to logs.



\---



\# 21. UI Requirements



\## 21.1 Login Screen



The login screen shall contain:



\* Username

\* Password

\* Login button

\* Error message area



\---



\## 21.2 Dashboard



The dashboard shall provide navigation to:



```text

Customer Management

Account Management

Transactions

Transaction History

Reports

Logout

```



It shall also display high-level statistics.



\---



\## 21.3 Customer Registration Screen



Fields:



\* Full name

\* Date of birth

\* Gender

\* Phone

\* Email

\* Address



Buttons:



\* Register

\* Clear

\* Back



\---



\## 21.4 Account Opening Screen



Fields:



\* Customer ID

\* Account type

\* Initial deposit where applicable



Buttons:



\* Open Account

\* Clear

\* Back



\---



\## 21.5 Deposit Screen



Fields:



\* Account number

\* Amount



Buttons:



\* Deposit

\* Clear

\* Back



\---



\## 21.6 Withdrawal Screen



Fields:



\* Account number

\* Amount



Buttons:



\* Withdraw

\* Clear

\* Back



\---



\## 21.7 Transfer Screen



Fields:



\* Source account

\* Destination account

\* Amount



Buttons:



\* Transfer

\* Clear

\* Back



\---



\## 21.8 Transaction History Screen



The interface shall display transaction records in a suitable Swing component such as `JTable`.



Possible columns:



```text

Transaction ID

Source Account

Destination Account

Type

Amount

Status

Date/Time

Description

```



\---



\# 22. Detailed Validation Requirements



The system shall validate user inputs before submitting data.



Examples:



\### Customer Name



Must not be empty.



\### Phone Number



Must follow the expected phone number format.



\### Email



Must follow an appropriate email format.



\### Amount



Must be numeric and greater than zero.



\### Account Number



Must exist before performing account operations.



\### Customer ID



Must identify an existing customer for account creation.



\---



\# 23. Error Message Requirements



Errors shall be understandable.



Instead of:



```text

SQLException: Error Code 1452...

```



The user interface should display:



```text

Unable to complete the operation.

Please verify the account details and try again.

```



Technical details may be logged for developers.



\---



\# 24. Assumptions



The system shall be developed under the following assumptions:



1\. The application is intended for bank employees.

2\. Employees have valid credentials.

3\. MySQL is available on the configured machine/server.

4\. The application has permission to access the database.

5\. Users possess basic computer literacy.

6\. Account numbers are generated by the application.

7\. Customer IDs are unique.

8\. The project represents a banking simulation.

9\. No real money is processed.

10\. The initial implementation will operate on a relatively small dataset.

11\. Internet connectivity is not required for normal local operation.

12\. The application will be executed in a controlled environment.



\---



\# 25. Dependencies



The system depends on:



\* Java 17

\* MySQL 8

\* MySQL JDBC driver

\* IntelliJ IDEA or equivalent Java IDE

\* Compatible operating system

\* Configured database credentials

\* Correct database schema



\---



\# 26. Constraints



\## Technical Constraints



\* Java shall be used as the primary programming language.

\* Java Swing shall be used for GUI development.

\* JDBC shall be used for database connectivity.

\* MySQL 8 shall be used as the database.

\* The system shall use Java 17.



\## Project Constraints



\* The system is intended for academic purposes.

\* Development time and resources are limited.

\* Advanced enterprise infrastructure is outside the project scope.



\---



\# 27. Out of Scope



The following features are outside the initial project scope:



\* Real banking network integration

\* ATM hardware integration

\* Credit/debit card processing

\* Online banking

\* Mobile banking

\* UPI integration

\* Real payment gateway integration

\* SMS gateway

\* Email notification infrastructure

\* Biometric authentication

\* Blockchain integration

\* Production-grade distributed banking infrastructure

\* Real currency settlement



These may be considered future enhancements.



\---



\# 28. Acceptance Criteria



The system shall be considered functionally acceptable when the following conditions are satisfied.



\## Authentication



\* Valid employee credentials successfully log in.

\* Invalid credentials are rejected.

\* Logout functions correctly.



\## Customer Management



\* Customer can be registered.

\* Duplicate customer identifiers are prevented.

\* Customer can be searched.

\* Customer information can be updated.



\## Account Management



\* Savings account can be opened.

\* Current account can be opened.

\* Account number is unique.

\* Customer can own multiple accounts.



\## Deposit



\* Valid deposits increase balance.

\* Invalid amounts are rejected.

\* Transaction record is created.



\## Withdrawal



\* Valid withdrawals decrease balance.

\* Savings minimum balance is enforced.

\* Current account overdraft limit is enforced.

\* Transaction record is created.



\## Transfer



\* Valid transfers debit the source account.

\* Valid transfers credit the destination account.

\* Failed transfers roll back.

\* Transaction status is recorded.



\## Transaction History



\* Transactions are persisted.

\* Transaction history can be retrieved.

\* Transaction information is displayed correctly.



\## Database



\* Foreign keys work correctly.

\* Invalid relationships are rejected.

\* Data persists between application sessions.



\## OOP



The project demonstrates:



\* Inheritance

\* Abstraction

\* Interfaces

\* Polymorphism

\* Overloading

\* Overriding

\* Encapsulation

\* Collections

\* Exception handling



\## Multithreading



The project demonstrates safe concurrent operations without producing inconsistent account balances.



\---



\# 29. Requirement Prioritization



Requirements shall be categorized as follows.



\## Must Have



\* Employee login

\* Customer registration

\* Customer search

\* Customer update

\* Savings account

\* Current account

\* Deposit

\* Withdrawal

\* Fund transfer

\* Balance enquiry

\* Transaction history

\* MySQL persistence

\* JDBC

\* Exception handling



\## Should Have



\* Dashboard statistics

\* Account closure

\* Role-based employee access

\* Application logging



\## Could Have



\* Interest calculation

\* Advanced reports

\* Export transaction history

\* Additional filtering



\## Future



\* Notifications

\* Mobile application

\* Online banking

\* External payment integration



\---



\# 30. Traceability



The requirements shall be traced throughout the development lifecycle.



Example:



```text

Business Requirement

&#x20;       ↓

Functional Requirement

&#x20;       ↓

Use Case

&#x20;       ↓

Database Design

&#x20;       ↓

Class Design

&#x20;       ↓

Implementation

&#x20;       ↓

Test Case

&#x20;       ↓

Test Result

```



For example:



```text

BR-005

Current Account Overdraft

&#x20;       ↓

FR-WD-004

Enforce overdraft limit

&#x20;       ↓

UC-08

Withdraw Money

&#x20;       ↓

CurrentAccount

&#x20;       ↓

WithdrawalService

&#x20;       ↓

Withdrawal Test Cases

```



A formal Requirements Traceability Matrix shall be created during the testing/documentation phase.



\---



\# 31. System-Level Use Case List



| ID    | Use Case                 | Primary Actor    | Priority |

| ----- | ------------------------ | ---------------- | -------- |

| UC-01 | Employee Login           | Employee         | Must     |

| UC-02 | Register Customer        | Employee         | Must     |

| UC-03 | Search Customer          | Employee         | Must     |

| UC-04 | Update Customer          | Employee         | Must     |

| UC-05 | Open Savings Account     | Employee         | Must     |

| UC-06 | Open Current Account     | Employee         | Must     |

| UC-07 | Deposit Money            | Employee         | Must     |

| UC-08 | Withdraw Money           | Employee         | Must     |

| UC-09 | Transfer Funds           | Employee         | Must     |

| UC-10 | Balance Enquiry          | Employee         | Must     |

| UC-11 | View Transaction History | Employee         | Must     |

| UC-12 | Dashboard Statistics     | Employee/Manager | Should   |

| UC-13 | Close Account            | Employee         | Should   |

| UC-14 | Calculate Interest       | Employee/System  | Could    |

| UC-15 | Logout                   | Employee         | Must     |



\---



\# 32. System Behaviour Overview



A typical banking operation follows this flow:



```text

Employee

&#x20;  |

&#x20;  v

Swing UI

&#x20;  |

&#x20;  v

Service Layer

&#x20;  |

&#x20;  +---- Validate Input

&#x20;  |

&#x20;  +---- Apply Business Rules

&#x20;  |

&#x20;  v

DAO Layer

&#x20;  |

&#x20;  v

JDBC

&#x20;  |

&#x20;  v

MySQL

```



For a transfer:



```text

Employee

&#x20;  |

&#x20;  v

Transfer UI

&#x20;  |

&#x20;  v

TransactionService

&#x20;  |

&#x20;  +---- Validate source

&#x20;  |

&#x20;  +---- Validate destination

&#x20;  |

&#x20;  +---- Validate amount

&#x20;  |

&#x20;  +---- Check balance rule

&#x20;  |

&#x20;  v

BEGIN TRANSACTION

&#x20;  |

&#x20;  +---- Debit Source

&#x20;  |

&#x20;  +---- Credit Destination

&#x20;  |

&#x20;  +---- Insert Transaction

&#x20;  |

&#x20;  v

COMMIT

```



If any operation fails:



```text

Failure

&#x20;  |

&#x20;  v

ROLLBACK

&#x20;  |

&#x20;  v

No partial transfer

```



\---



\# 33. Quality Attributes



The system shall emphasize the following quality attributes:



```text

Correctness

Reliability

Maintainability

Security

Usability

Performance

Data Integrity

Testability

Extensibility

```



The architecture shall support these qualities by separating responsibilities across layers.



\---



\# 34. Design Principles



The implementation should follow established software engineering principles.



\## Single Responsibility Principle



Classes should have focused responsibilities.



For example:



```text

CustomerDAO

```



should handle customer persistence rather than Swing UI operations.



\---



\## Separation of Concerns



The UI, business logic, and database operations shall remain separate.



\---



\## Encapsulation



Internal state shall be protected from uncontrolled external modification.



\---



\## Low Coupling



Modules should minimize unnecessary dependencies.



\---



\## High Cohesion



Each class should contain closely related responsibilities.



\---



\# 35. Future Enhancements



Potential future versions may include:



\## Version 2



\* Role-based authorization

\* Manager dashboard

\* PDF transaction statements

\* CSV export

\* Advanced search

\* Improved logging



\## Version 3



\* REST API

\* Web application

\* Mobile application

\* Notification system

\* Cloud database



\## Version 4



\* Enterprise banking integrations

\* External payment services

\* Advanced fraud detection

\* Distributed architecture



These features are not required for the current academic version.



\---



\# 36. Glossary



| Term           | Definition                                             |

| -------------- | ------------------------------------------------------ |

| Account        | A banking record belonging to a customer               |

| Account Number | Unique identifier of an account                        |

| Customer       | Person who owns one or more accounts                   |

| DAO            | Data Access Object                                     |

| Employee       | Authorized user of the banking application             |

| GUI            | Graphical User Interface                               |

| JDBC           | Java Database Connectivity                             |

| OOP            | Object-Oriented Programming                            |

| SQL            | Structured Query Language                              |

| SRS            | Software Requirements Specification                    |

| Transaction    | Financial operation affecting an account               |

| Deposit        | Addition of money to an account                        |

| Withdrawal     | Removal of money from an account                       |

| Transfer       | Movement of money from one account to another          |

| Atomicity      | Transaction property ensuring all-or-nothing execution |

| Overdraft      | Permitted negative account balance                     |

| Swing          | Java GUI framework                                     |



\---



\# 37. Appendix A — Requirement ID Convention



Requirements shall use the following naming convention:



```text

FR-XXX-NNN

NFR-XXX-NNN

BR-NNN

UC-NN

```



Where:



```text

FR  = Functional Requirement

NFR = Non-Functional Requirement

BR  = Business Rule

UC  = Use Case

```



Examples:



```text

FR-AUTH-001

FR-CUST-001

FR-ACC-001

FR-DEP-001

FR-WD-001

FR-TRF-001



NFR-SEC-001

NFR-PERF-001

NFR-REL-001



BR-001

BR-002

```



\---



\# 38. Appendix B — Proposed Domain Model



The conceptual object model is:



```text

&#x20;                   Person

&#x20;                     |

&#x20;            +--------+--------+

&#x20;            |                 |

&#x20;         Customer          Employee





&#x20;                   Account

&#x20;                     |

&#x20;            +--------+--------+

&#x20;            |                 |

&#x20;     SavingsAccount      CurrentAccount





Customer 1 -------- \* Account



Account 1 -------- \* Transaction



Employee -------- performs --------> Banking Operations

```



This model will be refined during UML class design.



\---



\# 39. Appendix C — Requirements-to-Implementation Mapping



| Requirement Area    | Primary Implementation                |

| ------------------- | ------------------------------------- |

| Authentication      | AuthenticationService + EmployeeDAO   |

| Customer Management | CustomerService + CustomerDAO         |

| Account Management  | AccountService + AccountDAO           |

| Deposit             | TransactionService + AccountDAO       |

| Withdrawal          | TransactionService + AccountDAO       |

| Transfer            | TransactionService + JDBC transaction |

| Transaction History | TransactionService + TransactionDAO   |

| Dashboard           | DashboardService                      |

| Validation          | ValidationUtil                        |

| Exceptions          | exception package                     |

| Database            | MySQL + JDBC                          |

| GUI                 | Java Swing                            |

| OOP                 | model package                         |

| Multithreading      | transaction/concurrency demonstration |



\---



\# 40. Appendix D — Development Sequence



The project shall follow the development sequence below.



\## Phase 1 — Requirements Engineering



1\. SRS

2\. Requirement analysis

3\. Functional requirements

4\. Non-functional requirements

5\. Business rules

6\. Use cases



\## Phase 2 — System Design



7\. Use Case Diagram

8\. ER Diagram

9\. Database design

10\. SQL schema

11\. UML Class Diagram

12\. Sequence Diagrams

13\. Activity Diagrams

14\. Architecture design

15\. Swing wireframes



\## Phase 3 — Implementation



16\. Project setup

17\. Database setup

18\. Model classes

19\. DAO layer

20\. Service layer

21\. Exception layer

22\. Swing UI

23\. Authentication

24\. Customer management

25\. Account management

26\. Transactions

27\. Dashboard

28\. Multithreading



\## Phase 4 — Testing



29\. Unit testing

30\. Integration testing

31\. Database testing

32\. UI testing

33\. Functional testing

34\. Negative testing

35\. Concurrency testing

36\. Regression testing



\## Phase 5 — Documentation



37\. Final report

38\. Screenshots

39\. Test results

40\. UML diagrams

41\. Database documentation

42\. Installation guide

43\. User manual

44\. Final project presentation



\---



\# 41. Final System Requirement Summary



The Banking Management System shall provide an employee-facing desktop application capable of managing customers, accounts, and banking transactions.



The system shall:



1\. Authenticate employees.

2\. Manage customer records.

3\. Support multiple accounts per customer.

4\. Support Savings and Current accounts.

5\. Generate unique account numbers.

6\. Process deposits.

7\. Process withdrawals.

8\. Enforce account-specific balance rules.

9\. Process fund transfers atomically.

10\. Maintain transaction history.

11\. Provide dashboard statistics.

12\. Persist data in MySQL.

13\. Use JDBC for database connectivity.

14\. Apply Java OOP principles.

15\. Use appropriate collections.

16\. Implement exception handling.

17\. Demonstrate multithreading.

18\. Maintain data integrity.

19\. Provide a usable Java Swing interface.

20\. Follow a maintainable three-tier architecture.



\---



\# 42. SRS Approval



This SRS shall serve as the baseline specification for subsequent system design and implementation.



Any significant change to the requirements should be documented and reviewed before implementation.



\---



\## End of Software Requirements Specification



