# Activity Diagram

## Banking Management System

The Activity Diagram represents the flow of activities performed within the
Banking Management System.

It describes how users interact with the system and how banking operations
progress from the initial request to completion.

The major workflows covered are:

- User authentication
- Customer registration
- Account creation
- Deposit
- Withdrawal
- Fund transfer
- Transaction processing

---

## Actors

The primary actors involved in the workflows are:

- **Admin** – Performs administrative and system management activities.
- **Employee** – Performs day-to-day banking operations.
- **Customer** – Performs personal banking activities.

---

## 1. General System Activity Flow

The general flow of the Banking Management System is:

```mermaid
flowchart TD

    Start([Start])
    Login["Enter Login Credentials"]
    Validate["Validate Credentials"]

    Valid{"Credentials Valid?"}

    Access["Grant System Access"]
    Error["Display Login Error"]

    Operation["Select Banking Operation"]
    Process["Process Request"]
    Result["Display Result"]

    Logout["Logout"]
    End([End])

    Start --> Login
    Login --> Validate
    Validate --> Valid

    Valid -- No --> Error
    Error --> Login

    Valid -- Yes --> Access
    Access --> Operation
    Operation --> Process
    Process --> Result
    Result --> Logout
    Logout --> End
```

---

# 2. Customer Registration Activity

The customer registration workflow allows an authorized employee to create a
new customer record.

```mermaid
flowchart TD

    Start([Start])

    Login["Employee Login"]
    Authenticate["Authenticate Employee"]

    AuthValid{"Authentication Successful?"}

    LoginError["Display Login Error"]

    EnterDetails["Enter Customer Details"]
    ValidateDetails["Validate Customer Information"]

    DetailsValid{"Customer Information Valid?"}

    ValidationError["Display Validation Error"]

    CheckCustomer["Check Existing Customer"]

    Existing{"Customer Already Exists?"}

    ExistingError["Display Customer Already Exists"]

    CreateCustomer["Create Customer Record"]
    SaveCustomer["Save Customer to Database"]

    Success["Display Registration Success"]

    End([End])

    Start --> Login
    Login --> Authenticate
    Authenticate --> AuthValid

    AuthValid -- No --> LoginError
    LoginError --> Login

    AuthValid -- Yes --> EnterDetails
    EnterDetails --> ValidateDetails
    ValidateDetails --> DetailsValid

    DetailsValid -- No --> ValidationError
    ValidationError --> EnterDetails

    DetailsValid -- Yes --> CheckCustomer
    CheckCustomer --> Existing

    Existing -- Yes --> ExistingError
    ExistingError --> End

    Existing -- No --> CreateCustomer
    CreateCustomer --> SaveCustomer
    SaveCustomer --> Success
    Success --> End
```

---

# 3. Account Creation Activity

The account creation workflow allows an authorized employee to create a
bank account for an existing customer.

```mermaid
flowchart TD

    Start([Start])

    Login["Employee Login"]
    Authenticate["Authenticate Employee"]

    AuthValid{"Authentication Successful?"}

    LoginError["Display Login Error"]

    SelectCustomer["Select Customer"]
    VerifyCustomer["Verify Customer"]

    CustomerExists{"Customer Exists?"}

    CustomerError["Display Customer Not Found"]

    EnterAccount["Enter Account Details"]
    ValidateAccount["Validate Account Information"]

    AccountValid{"Account Information Valid?"}

    ValidationError["Display Validation Error"]

    GenerateAccount["Generate Account Number"]
    CreateAccount["Create Account Record"]
    SaveAccount["Save Account to Database"]

    Success["Display Account Creation Success"]

    End([End])

    Start --> Login
    Login --> Authenticate
    Authenticate --> AuthValid

    AuthValid -- No --> LoginError
    LoginError --> Login

    AuthValid -- Yes --> SelectCustomer
    SelectCustomer --> VerifyCustomer
    VerifyCustomer --> CustomerExists

    CustomerExists -- No --> CustomerError
    CustomerError --> End

    CustomerExists -- Yes --> EnterAccount
    EnterAccount --> ValidateAccount
    ValidateAccount --> AccountValid

    AccountValid -- No --> ValidationError
    ValidationError --> EnterAccount

    AccountValid -- Yes --> GenerateAccount
    GenerateAccount --> CreateAccount
    CreateAccount --> SaveAccount
    SaveAccount --> Success
    Success --> End
```

---

# 4. Deposit Activity

The deposit workflow represents adding money to a customer's account.

```mermaid
flowchart TD

    Start([Start])

    Request["Initiate Deposit"]
    Identify["Enter Account Number"]
    FindAccount["Find Account"]

    AccountExists{"Account Exists?"}

    AccountError["Display Account Not Found"]

    CheckStatus["Check Account Status"]

    Active{"Account Active?"}

    InactiveError["Display Account Inactive"]

    EnterAmount["Enter Deposit Amount"]
    ValidateAmount["Validate Amount"]

    AmountValid{"Amount Valid?"}

    AmountError["Display Invalid Amount"]

    UpdateBalance["Update Account Balance"]
    RecordTransaction["Create Transaction Record"]

    Success["Display Deposit Successful"]

    End([End])

    Start --> Request
    Request --> Identify
    Identify --> FindAccount
    FindAccount --> AccountExists

    AccountExists -- No --> AccountError
    AccountError --> End

    AccountExists -- Yes --> CheckStatus
    CheckStatus --> Active

    Active -- No --> InactiveError
    InactiveError --> End

    Active -- Yes --> EnterAmount
    EnterAmount --> ValidateAmount
    ValidateAmount --> AmountValid

    AmountValid -- No --> AmountError
    AmountError --> EnterAmount

    AmountValid -- Yes --> UpdateBalance
    UpdateBalance --> RecordTransaction
    RecordTransaction --> Success
    Success --> End
```

---

# 5. Withdrawal Activity

The withdrawal workflow represents removing money from a customer's account.

```mermaid
flowchart TD

    Start([Start])

    Request["Initiate Withdrawal"]
    Identify["Enter Account Number"]
    FindAccount["Find Account"]

    AccountExists{"Account Exists?"}

    AccountError["Display Account Not Found"]

    CheckStatus["Check Account Status"]

    Active{"Account Active?"}

    InactiveError["Display Account Inactive"]

    EnterAmount["Enter Withdrawal Amount"]
    ValidateAmount["Validate Amount"]

    AmountValid{"Amount Valid?"}

    AmountError["Display Invalid Amount"]

    CheckBalance["Check Available Balance"]

    Sufficient{"Sufficient Balance?"}

    Insufficient["Display Insufficient Balance"]

    UpdateBalance["Update Account Balance"]
    RecordTransaction["Create Transaction Record"]

    Success["Display Withdrawal Successful"]

    End([End])

    Start --> Request
    Request --> Identify
    Identify --> FindAccount
    FindAccount --> AccountExists

    AccountExists -- No --> AccountError
    AccountError --> End

    AccountExists -- Yes --> CheckStatus
    CheckStatus --> Active

    Active -- No --> InactiveError
    InactiveError --> End

    Active -- Yes --> EnterAmount
    EnterAmount --> ValidateAmount
    ValidateAmount --> AmountValid

    AmountValid -- No --> AmountError
    AmountError --> EnterAmount

    AmountValid -- Yes --> CheckBalance
    CheckBalance --> Sufficient

    Sufficient -- No --> Insufficient
    Insufficient --> End

    Sufficient -- Yes --> UpdateBalance
    UpdateBalance --> RecordTransaction
    RecordTransaction --> Success
    Success --> End
```

---

# 6. Fund Transfer Activity

The fund transfer workflow represents transferring money from one account to
another account.

```mermaid
flowchart TD

    Start([Start])

    Request["Initiate Fund Transfer"]

    Source["Enter Source Account"]
    Destination["Enter Destination Account"]
    Amount["Enter Transfer Amount"]

    FindSource["Find Source Account"]
    FindDestination["Find Destination Account"]

    SourceExists{"Source Account Exists?"}
    DestinationExists{"Destination Account Exists?"}

    SourceError["Display Source Account Not Found"]
    DestinationError["Display Destination Account Not Found"]

    CheckStatus["Check Account Status"]

    AccountsActive{"Both Accounts Active?"}

    StatusError["Display Account Status Error"]

    ValidateAmount["Validate Transfer Amount"]

    AmountValid{"Amount Valid?"}

    AmountError["Display Invalid Amount"]

    CheckBalance["Check Source Account Balance"]

    Sufficient{"Sufficient Balance?"}

    Insufficient["Display Insufficient Balance"]

    Debit["Debit Source Account"]
    Credit["Credit Destination Account"]

    RecordTransaction["Create Transfer Transaction Record"]

    Success["Display Transfer Successful"]

    End([End])

    Start --> Request

    Request --> Source
    Source --> Destination
    Destination --> Amount

    Amount --> FindSource
    FindSource --> SourceExists

    SourceExists -- No --> SourceError
    SourceError --> End

    SourceExists -- Yes --> FindDestination
    FindDestination --> DestinationExists

    DestinationExists -- No --> DestinationError
    DestinationError --> End

    DestinationExists -- Yes --> CheckStatus
    CheckStatus --> AccountsActive

    AccountsActive -- No --> StatusError
    StatusError --> End

    AccountsActive -- Yes --> ValidateAmount
    ValidateAmount --> AmountValid

    AmountValid -- No --> AmountError
    AmountError --> Amount

    AmountValid -- Yes --> CheckBalance
    CheckBalance --> Sufficient

    Sufficient -- No --> Insufficient
    Insufficient --> End

    Sufficient -- Yes --> Debit
    Debit --> Credit
    Credit --> RecordTransaction
    RecordTransaction --> Success
    Success --> End
```

---

# 7. Transaction Processing Flow

The transaction processing workflow represents the common processing path for
deposit, withdrawal, and transfer operations.

```mermaid
flowchart TD

    Start([Start])

    Request["Receive Transaction Request"]

    Validate["Validate Transaction"]

    Valid{"Transaction Valid?"}

    Reject["Reject Transaction"]
    Process["Process Transaction"]

    UpdateAccount["Update Account Balance"]
    Record["Create Transaction Record"]

    Successful["Transaction Successful"]

    Failed["Transaction Failed"]

    Result["Return Transaction Result"]

    End([End])

    Start --> Request
    Request --> Validate
    Validate --> Valid

    Valid -- No --> Reject
    Reject --> Failed
    Failed --> Result

    Valid -- Yes --> Process
    Process --> UpdateAccount
    UpdateAccount --> Record
    Record --> Successful
    Successful --> Result

    Result --> End
```

---

# 8. High-Level Banking Operation Flow

The main banking operations can be represented by the following high-level
workflow:

```mermaid
flowchart TD

    Start([Start])

    Login["User Login"]
    Authenticate["Authenticate User"]

    Valid{"Valid User?"}

    LoginError["Login Failed"]

    Dashboard["Open Dashboard"]

    Operation{"Select Operation"}

    Customer["Customer Management"]
    Account["Account Management"]
    Deposit["Deposit"]
    Withdrawal["Withdrawal"]
    Transfer["Fund Transfer"]
    Transactions["Transaction History"]
    Reports["Reports"]

    Process["Process Selected Operation"]

    Success["Display Result"]

    Continue{"Perform Another Operation?"}

    Logout["Logout"]

    End([End])

    Start --> Login
    Login --> Authenticate
    Authenticate --> Valid

    Valid -- No --> LoginError
    LoginError --> Login

    Valid -- Yes --> Dashboard
    Dashboard --> Operation

    Operation --> Customer
    Operation --> Account
    Operation --> Deposit
    Operation --> Withdrawal
    Operation --> Transfer
    Operation --> Transactions
    Operation --> Reports

    Customer --> Process
    Account --> Process
    Deposit --> Process
    Withdrawal --> Process
    Transfer --> Process
    Transactions --> Process
    Reports --> Process

    Process --> Success
    Success --> Continue

    Continue -- Yes --> Dashboard
    Continue -- No --> Logout

    Logout --> End
```

---

# Activity Flow Summary

| Workflow | Main Activities |
|---|---|
| Authentication | Login → Validate Credentials → Grant Access / Reject |
| Customer Registration | Enter Details → Validate → Check Existing Customer → Create Customer |
| Account Creation | Select Customer → Verify Customer → Enter Details → Generate Account → Create Account |
| Deposit | Identify Account → Validate Account → Validate Amount → Update Balance → Record Transaction |
| Withdrawal | Identify Account → Validate Account → Validate Amount → Check Balance → Update Balance → Record Transaction |
| Fund Transfer | Identify Accounts → Validate Accounts → Validate Amount → Check Balance → Debit → Credit → Record Transaction |
| Transaction Processing | Receive Request → Validate → Process → Update Balance → Record Transaction → Return Result |

---

# Relationship with Other Diagrams

The Activity Diagram represents the **behavioral workflow** of the Banking
Management System.

It complements the other system diagrams:

- **Architecture Diagram** – Describes the structural organization of the system.
- **ER Diagram** – Describes the database entities and their relationships.
- **Use Case Diagram** – Describes actors and system functionality.
- **Activity Diagram** – Describes the workflow of system operations.
- **Sequence Diagram** – Describes communication between system components over time.
- **Class Diagram** – Describes the application classes and their relationships.

Together, these diagrams provide structural, functional, data, and behavioral
views of the Banking Management System.