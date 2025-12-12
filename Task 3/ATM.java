import java.util.Scanner;

public class ATM {

    private User user;
    private Scanner sc;

    public ATM(User user) {
        this.user = user;
        this.sc = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    user.account.printHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 5);
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        if (user.account.withdraw(amount)) {
            System.out.println("Withdraw successful. Current Balance: " + user.account.getBalance());
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();

        user.account.deposit(amount);
        System.out.println("Deposit successful. Current Balance: " + user.account.getBalance());
    }

    private void transfer() {
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        // creating another account as receiver
        Account receiver = new Account(0);

        if (user.account.transfer(receiver, amount)) {
            System.out.println("Transfer successful. Current Balance: " + user.account.getBalance());
        } else {
            System.out.println("Insufficient funds!");
        }
    }
}
