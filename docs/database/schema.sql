-- ============================================================
-- Banking Management System
-- Database Schema
-- MySQL 8
-- ============================================================

---

-- 1. Create Database

---

DROP DATABASE IF EXISTS banking_management_system;

CREATE DATABASE IF NOT EXISTS banking_management_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

USE banking_management_system;

-- ============================================================
-- 2. Employees Table
-- ============================================================

CREATE TABLE IF NOT EXISTS employees (


                                         employee_id BIGINT AUTO_INCREMENT,

                                         employee_code VARCHAR(20) NOT NULL,

                                         username VARCHAR(50) NOT NULL,

                                         password_hash VARCHAR(255) NOT NULL,

                                         first_name VARCHAR(50) NOT NULL,

                                         last_name VARCHAR(50) NOT NULL,

                                         email VARCHAR(100) NOT NULL,

                                         phone VARCHAR(20),

                                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                             ON UPDATE CURRENT_TIMESTAMP,

                                         CONSTRAINT pk_employees
                                             PRIMARY KEY (employee_id),

                                         CONSTRAINT uk_employee_code
                                             UNIQUE (employee_code),

                                         CONSTRAINT uk_employee_username
                                             UNIQUE (username),

                                         CONSTRAINT uk_employee_email
                                             UNIQUE (email),

                                         CONSTRAINT chk_employee_status
                                             CHECK (status IN ('ACTIVE', 'INACTIVE'))


) ENGINE = InnoDB;

-- ============================================================
-- 3. Customers Table
-- ============================================================

CREATE TABLE IF NOT EXISTS customers (


                                         customer_id BIGINT AUTO_INCREMENT,

                                         customer_code VARCHAR(20) NOT NULL,

                                         first_name VARCHAR(50) NOT NULL,

                                         last_name VARCHAR(50) NOT NULL,

                                         date_of_birth DATE NOT NULL,

                                         gender VARCHAR(20),

                                         email VARCHAR(100),

                                         phone VARCHAR(20) NOT NULL,

                                         address VARCHAR(255) NOT NULL,

                                         city VARCHAR(50) NOT NULL,

                                         state VARCHAR(50) NOT NULL,

                                         postal_code VARCHAR(15) NOT NULL,

                                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                             ON UPDATE CURRENT_TIMESTAMP,

                                         CONSTRAINT pk_customers
                                             PRIMARY KEY (customer_id),

                                         CONSTRAINT uk_customer_code
                                             UNIQUE (customer_code),

                                         CONSTRAINT chk_customer_status
                                             CHECK (status IN ('ACTIVE', 'INACTIVE'))


) ENGINE = InnoDB;

-- ============================================================
-- 4. Accounts Table
-- ============================================================

CREATE TABLE IF NOT EXISTS accounts (


                                        account_id BIGINT AUTO_INCREMENT,

                                        account_number VARCHAR(20) NOT NULL,

                                        customer_id BIGINT NOT NULL,

                                        account_type VARCHAR(20) NOT NULL,

                                        balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,

                                        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                                        opened_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                        closed_at DATETIME,

                                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                                            ON UPDATE CURRENT_TIMESTAMP,

                                        CONSTRAINT pk_accounts
                                            PRIMARY KEY (account_id),

                                        CONSTRAINT uk_account_number
                                            UNIQUE (account_number),

                                        CONSTRAINT fk_account_customer
                                            FOREIGN KEY (customer_id)
                                            REFERENCES customers(customer_id)
                                            ON DELETE RESTRICT
                                            ON UPDATE RESTRICT,

                                        CONSTRAINT chk_account_type
                                            CHECK (account_type IN ('SAVINGS', 'CURRENT')),

                                        CONSTRAINT chk_account_status
                                            CHECK (status IN ('ACTIVE', 'CLOSED')),

                                        CONSTRAINT chk_account_balance
                                            CHECK (
                                                (account_type = 'SAVINGS' AND balance >= 1000.00)
                                                OR
                                                (account_type = 'CURRENT' AND balance >= -5000.00)
                                            )


) ENGINE = InnoDB;

-- ============================================================
-- 5. Transactions Table
-- ============================================================

CREATE TABLE IF NOT EXISTS transactions (


                                            transaction_id BIGINT AUTO_INCREMENT,

                                            transaction_reference VARCHAR(30) NOT NULL,

                                            account_id BIGINT NOT NULL,

                                            related_account_id BIGINT,

                                            employee_id BIGINT NOT NULL,

                                            transaction_type VARCHAR(20) NOT NULL,

                                            amount DECIMAL(15,2) NOT NULL,

                                            status VARCHAR(20) NOT NULL,

                                            description VARCHAR(255),

                                            transaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                            CONSTRAINT pk_transactions
                                                PRIMARY KEY (transaction_id),

                                            CONSTRAINT uk_transaction_reference
                                                UNIQUE (transaction_reference),

                                            CONSTRAINT fk_transaction_account
                                                FOREIGN KEY (account_id)
                                                REFERENCES accounts(account_id)
                                                ON DELETE RESTRICT
                                                ON UPDATE RESTRICT,

                                            CONSTRAINT fk_transaction_related_account
                                                FOREIGN KEY (related_account_id)
                                                REFERENCES accounts(account_id)
                                                ON DELETE RESTRICT
                                                ON UPDATE RESTRICT,

                                            CONSTRAINT fk_transaction_employee
                                                FOREIGN KEY (employee_id)
                                                REFERENCES employees(employee_id)
                                                ON DELETE RESTRICT
                                                ON UPDATE RESTRICT,

                                            CONSTRAINT chk_transaction_type
                                                CHECK (
                                                    transaction_type IN (
                                                        'DEPOSIT',
                                                        'WITHDRAWAL',
                                                        'TRANSFER',
                                                        'INTEREST'
                                                    )
                                                ),

                                            CONSTRAINT chk_transaction_status
                                                CHECK (
                                                    status IN ('SUCCESS', 'FAILED')
                                                ),

                                            CONSTRAINT chk_transaction_amount
                                                CHECK (amount > 0)


) ENGINE = InnoDB;

-- ============================================================
-- 6. Indexes
-- ============================================================

-- Customer search indexes

CREATE INDEX idx_customers_phone
    ON customers(phone);

CREATE INDEX idx_customers_email
    ON customers(email);

-- Account indexes

CREATE INDEX idx_accounts_customer
    ON accounts(customer_id);

CREATE INDEX idx_accounts_customer_status
    ON accounts(customer_id, status);

-- Transaction indexes

CREATE INDEX idx_transactions_account
    ON transactions(account_id);

CREATE INDEX idx_transactions_related_account
    ON transactions(related_account_id);

CREATE INDEX idx_transactions_employee
    ON transactions(employee_id);

CREATE INDEX idx_transactions_date
    ON transactions(transaction_date);

CREATE INDEX idx_transactions_account_date
    ON transactions(account_id, transaction_date);

-- ============================================================
-- 7. Verify Tables
-- ============================================================

SHOW TABLES;
