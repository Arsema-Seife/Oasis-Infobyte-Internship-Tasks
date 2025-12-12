import java.util.*;

class Reservation {
    String pnr;
    String passengerName;
    String trainNo;
    String trainName;
    String classType;
    String journeyDate;
    String from;
    String to;

    Reservation(String pnr, String passengerName, String trainNo, 
                String trainName, String classType, String journeyDate,
                String from, String to) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.from = from;
        this.to = to;
    }

    void display() {
        System.out.println("\n--- Reservation Details ---");
        System.out.println("PNR Number     : " + pnr);
        System.out.println("Passenger Name : " + passengerName);
        System.out.println("Train No       : " + trainNo);
        System.out.println("Train Name     : " + trainName);
        System.out.println("Class Type     : " + classType);
        System.out.println("Journey Date   : " + journeyDate);
        System.out.println("From           : " + from);
        System.out.println("To             : " + to);
    }
}

public class OnlineReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Reservation> reservations = new ArrayList<>();

    // Hardcoded Login values
    static String validUser = "admin";
    static String validPass = "1234";

    public static void main(String[] args) {

        if (!login()) {
            System.out.println("Invalid Login. Exiting System...");
            return;
        }

        int choice;
        do {
            System.out.println("\n===== ONLINE RESERVATION SYSTEM =====");
            System.out.println("1. Make Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. View All Reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // clear buffer

            switch (choice) {
                case 1:
                    makeReservation();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    System.out.println("Exiting System... Thank You!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);
    }

    // LOGIN MODULE
    static boolean login() {
        System.out.println("===== LOGIN =====");
        System.out.print("Enter Username: ");
        String user = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        return (user.equals(validUser) && pass.equals(validPass));
    }

    // RESERVATION MODULE
    static void makeReservation() {
        System.out.println("\n--- Make Reservation ---");

        System.out.print("Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Train Number: ");
        String tno = sc.nextLine();

        // Train name auto-filled (simple logic)
        String tname = autoTrainName(tno);

        System.out.print("Class Type (Sleeper/AC/General): ");
        String classType = sc.nextLine();

        System.out.print("Journey Date (DD-MM-YYYY): ");
        String jdate = sc.nextLine();

        System.out.print("From: ");
        String from = sc.nextLine();

        System.out.print("To: ");
        String to = sc.nextLine();

        // Generate PNR
        String pnr = "PNR" + (1000 + reservations.size());

        Reservation r = new Reservation(pnr, name, tno, tname, classType, jdate, from, to);
        reservations.add(r);

        System.out.println("Reservation Successful!");
        System.out.println("Your PNR Number: " + pnr);
    }

    // CANCELLATION MODULE
    static void cancelReservation() {
        System.out.println("\n--- Cancel Reservation ---");
        System.out.print("Enter PNR Number: ");
        String pnr = sc.nextLine();

        Reservation found = null;

        for (Reservation r : reservations) {
            if (r.pnr.equals(pnr)) {
                found = r;
                break;
            }
        }

        if (found == null) {
            System.out.println("PNR Not Found!");
            return;
        }

        found.display();
        System.out.print("\nDo you really want to cancel? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            reservations.remove(found);
            System.out.println("Cancellation Successful!");
        } else {
            System.out.println("Cancellation Aborted!");
        }
    }

    // VIEW ALL
    static void viewAllReservations() {
        System.out.println("\n--- ALL RESERVATIONS ---");
        if (reservations.isEmpty()) {
            System.out.println("No Reservations Found.");
        } else {
            for (Reservation r : reservations) {
                r.display();
                System.out.println("---------------------------");
            }
        }
    }

    // Auto Train Name Logic
    static String autoTrainName(String trainNo) {
        if (trainNo.equals("1001")) return "Express Line";
        if (trainNo.equals("2002")) return "Mountain Rider";
        if (trainNo.equals("3003")) return "City Superfast";
        return "Unknown Train";
    }
}
