-- ============================================================
-- Banking Management System
-- Development Seed Data
-- MySQL 8
-- ============================================================

USE banking_management_system;

-- ============================================================
-- 1. Employees
-- ============================================================

INSERT INTO employees
(
    employee_code,
    username,
    password_hash,
    first_name,
    last_name,
    email,
    phone,
    role,
    status
)
VALUES
    (
        'EMP-001',
        'admin',
        'CHANGE_ME_HASH',
        'System',
        'Administrator',
        'admin@bank.com',
        '9999999999',
        'ADMIN',
        'ACTIVE'
    ),
    (
        'EMP-002',
        'employee01',
        'CHANGE_ME_HASH',
        'Bank',
        'Employee',
        'employee01@bank.com',
        '9999999998',
        'EMPLOYEE',
        'ACTIVE'
    );

-- ============================================================
-- 2. Customers
-- ============================================================

INSERT INTO customers
(
    customer_code,
    first_name,
    last_name,
    date_of_birth,
    gender,
    email,
    phone,
    address,
    city,
    state,
    postal_code,
    status
)
VALUES
    (
        'CUST-000001',
        'Rahul',
        'Sharma',
        '2000-05-15',
        'MALE',
        'rahul.sharma@example.com',
        '9876543210',
        '123 Main Street',
        'Bengaluru',
        'Karnataka',
        '560001',
        'ACTIVE'
    ),
    (
        'CUST-000002',
        'Priya',
        'Kaur',
        '2001-08-22',
        'FEMALE',
        'priya.kaur@example.com',
        '9876543211',
        '45 Park Avenue',
        'Bengaluru',
        'Karnataka',
        '560002',
        'ACTIVE'
    );

-- ============================================================
-- 3. Accounts
-- ============================================================

INSERT INTO accounts
(
    account_number,
    customer_id,
    account_type,
    balance,
    status
)
VALUES
    (
        '1000000001',
        1,
        'SAVINGS',
        10000.00,
        'ACTIVE'
    ),
    (
        '1000000002',
        1,
        'CURRENT',
        25000.00,
        'ACTIVE'
    ),
    (
        '1000000003',
        2,
        'SAVINGS',
        15000.00,
        'ACTIVE'
    );

-- ============================================================
-- 4. Transactions
-- ============================================================

INSERT INTO transactions
(
    transaction_reference,
    account_id,
    related_account_id,
    employee_id,
    transaction_type,
    amount,
    status,
    description
)
VALUES
    (
        'TXN-20260920-000001',
        1,
        NULL,
        1,
        'DEPOSIT',
        10000.00,
        'SUCCESS',
        'Initial account deposit'
    ),
    (
        'TXN-20260920-000002',
        3,
        NULL,
        2,
        'DEPOSIT',
        15000.00,
        'SUCCESS',
        'Initial account deposit'
    );

-- ============================================================
-- 5. Verification Queries
-- ============================================================

SELECT * FROM employees;

SELECT * FROM customers;

SELECT * FROM accounts;

SELECT * FROM transactions;