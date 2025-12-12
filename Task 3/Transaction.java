public class Transaction {
    String type;
    double amount;
    double balanceAfter;

    Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return type + ": " + amount + " | Balance After: " + balanceAfter;
    }
}
