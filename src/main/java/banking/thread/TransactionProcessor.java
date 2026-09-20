package banking.thread;

import banking.exception.*;
import banking.service.BankingService;

/**
 * Demonstrates multithreading in a banking context.
 *
 * Implements Runnable so it can be executed by a Thread.
 *
 * Demonstrates:
 * - Thread creation (new Thread(new TransactionProcessor(...)))
 * - Concurrent execution (multiple threads accessing the same account)
 * - Shared resource (the account balance via BankingService)
 * - Synchronization (BankingService methods are synchronized)
 *
 * Example usage (from the UI):
 *   TransactionProcessor p1 = new TransactionProcessor(service, "1000000001", 2000, "WITHDRAW", 1);
 *   TransactionProcessor p2 = new TransactionProcessor(service, "1000000001", 3000, "WITHDRAW", 1);
 *
 *   Thread t1 = new Thread(p1, "Thread-1");
 *   Thread t2 = new Thread(p2, "Thread-2");
 *
 *   t1.start();
 *   t2.start();
 *
 *   // Both threads try to withdraw from the same account concurrently.
 *   // The synchronized keyword in BankingService ensures the balance
 *   // is updated safely — no lost updates or inconsistent state.
 */
public class TransactionProcessor implements Runnable {

    private final BankingService bankingService;
    private final String accountNumber;
    private final double amount;
    private final String operationType; // "DEPOSIT" or "WITHDRAW"
    private final long employeeId;

    // Result of the operation — can be checked after thread completes
    private String result;
    private boolean success;

    public TransactionProcessor(BankingService bankingService, String accountNumber,
                                double amount, String operationType, long employeeId) {
        this.bankingService = bankingService;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.operationType = operationType;
        this.employeeId = employeeId;
        this.result = "";
        this.success = false;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Starting " + operationType +
                " of ₹" + String.format("%.2f", amount) +
                " on account " + accountNumber);

        try {
            if ("DEPOSIT".equals(operationType)) {
                bankingService.deposit(accountNumber, amount,
                        "Concurrent deposit by " + threadName, employeeId);
            } else if ("WITHDRAW".equals(operationType)) {
                bankingService.withdraw(accountNumber, amount,
                        "Concurrent withdrawal by " + threadName, employeeId);
            }

            result = "[" + threadName + "] " + operationType + " of ₹" +
                    String.format("%.2f", amount) + " — SUCCESS";
            success = true;
            System.out.println(result);

        } catch (InsufficientBalanceException e) {
            result = "[" + threadName + "] " + operationType + " of ₹" +
                    String.format("%.2f", amount) + " — FAILED: " + e.getMessage();
            success = false;
            System.out.println(result);

        } catch (InvalidAccountException | InvalidAmountException | TransactionFailedException e) {
            result = "[" + threadName + "] " + operationType + " of ₹" +
                    String.format("%.2f", amount) + " — FAILED: " + e.getMessage();
            success = false;
            System.out.println(result);
        }
    }

    // ── Getters for checking results after thread completes ────────

    public String getResult() { return result; }
    public boolean isSuccess() { return success; }
}
