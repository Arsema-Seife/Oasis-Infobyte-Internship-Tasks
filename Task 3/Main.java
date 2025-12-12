import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // create one user for the demo
        User user = new User("admin", "1234", 5000);

        System.out.println("===== Welcome to the ATM System =====");

        System.out.print("Enter User ID: ");
        String id = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        if (user.authenticate(id, pin)) {
            System.out.println("\nLogin successful!");
            ATM atm = new ATM(user);
            atm.showMenu();
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }
    }
}
