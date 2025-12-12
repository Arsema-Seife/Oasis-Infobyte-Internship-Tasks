import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User u = new User("admin", "1234", "Student Name", "student@email.com");

        System.out.println("===== ONLINE EXAMINATION SYSTEM =====");

        System.out.print("Enter Username: ");
        String user = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        if (!u.authenticate(user, pass)) {
            System.out.println("Invalid login! Exiting...");
            return;
        }

        System.out.println("\nLogin Successful! Welcome " + u.getName());

        int choice;
        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start Examination");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter new email: ");
                    String email = sc.nextLine();
                    u.updateProfile(name, email);
                    break;

                case 2:
                    System.out.print("Enter old password: ");
                    String oldp = sc.nextLine();
                    System.out.print("Enter new password: ");
                    String newp = sc.nextLine();
                    u.updatePassword(oldp, newp);
                    break;

                case 3:
                    System.out.println("\nStarting Exam...");
                    Exam exam = new Exam();
                    exam.startExam();
                    break;

                case 4:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid Option!");
            }

        } while (choice != 4);
    }
}
