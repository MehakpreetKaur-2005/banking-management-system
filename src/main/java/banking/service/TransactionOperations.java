package banking.service;

import banking.exception.*;

/**
 * Interface for banking transaction operations.
 *
 * Demonstrates:
 * - Interface usage as required by the assignment
 * - Defines the contract for deposit, withdraw, and transfer operations
 *
 * Implemented by BankingService.
 */
public interface TransactionOperations {

    /**
     * Deposit money into an account.
     *
     * @param accountNumber the target account number
     * @param amount        the deposit amount (must be > 0)
     * @param description   optional description for the transaction
     * @param employeeId    the employee performing the operation
     * @throws InvalidAccountException if the account does not exist or is closed
     * @throws InvalidAmountException  if the amount is <= 0
     * @throws TransactionFailedException if the transaction cannot be completed
     */
    void deposit(String accountNumber, double amount, String description, long employeeId)
            throws InvalidAccountException, InvalidAmountException, TransactionFailedException;

    /**
     * Withdraw money from an account.
     *
     * @param accountNumber the source account number
     * @param amount        the withdrawal amount (must be > 0)
     * @param description   optional description for the transaction
     * @param employeeId    the employee performing the operation
     * @throws InvalidAccountException       if the account does not exist or is closed
     * @throws InvalidAmountException        if the amount is <= 0
     * @throws InsufficientBalanceException  if withdrawal violates minimum balance rule
     * @throws TransactionFailedException    if the transaction cannot be completed
     */
    void withdraw(String accountNumber, double amount, String description, long employeeId)
            throws InvalidAccountException, InvalidAmountException,
                   InsufficientBalanceException, TransactionFailedException;

    /**
     * Transfer money between two accounts.
     *
     * @param fromAccountNumber the source account number
     * @param toAccountNumber   the destination account number
     * @param amount            the transfer amount (must be > 0)
     * @param employeeId        the employee performing the operation
     * @throws InvalidAccountException       if either account does not exist or is closed
     * @throws InvalidAmountException        if the amount is <= 0
     * @throws InsufficientBalanceException  if source account balance is insufficient
     * @throws TransactionFailedException    if the transaction cannot be completed
     */
    void transfer(String fromAccountNumber, String toAccountNumber, double amount, long employeeId)
            throws InvalidAccountException, InvalidAmountException,
                   InsufficientBalanceException, TransactionFailedException;
}
