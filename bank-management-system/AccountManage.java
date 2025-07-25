import java.util.ArrayList;
import java.util.List;

public class AccountManage {
    private String name;
    private double balance = 0.0;
    private List<String> transactions = new ArrayList<>();

    public AccountManage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited ₹" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance < amount) return false;
        balance -= amount;
        transactions.add("Withdrew ₹" + amount);
        return true;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public String toFileString() {
        return name + ":" + balance + ":" + String.join("|", transactions);
    }

    public static AccountManage fromFileString(String data) {
        String[] parts = data.split(":");
        AccountManage acc = new AccountManage(parts[0]);
        acc.balance = Double.parseDouble(parts[1]);
        if (parts.length > 2) {
            for (String txn : parts[2].split("\\|")) {
                acc.transactions.add(txn);
            }
        }
        return acc;
    }
}