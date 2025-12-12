import java.util.ArrayList;

public class Account {
    private double balance;
    private ArrayList<Transaction> history;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.history = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        history.add(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        history.add(new Transaction("Withdraw", amount, balance));
        return true;
    }

    public boolean transfer(Account receiver, double amount) {
        if (amount > balance) return false;
        balance -= amount;
        receiver.balance += amount;

        history.add(new Transaction("Transfer Sent", amount, balance));
        receiver.history.add(new Transaction("Transfer Received", amount, receiver.balance));
        return true;
    }

    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}
