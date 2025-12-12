import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Console UI for Library management.
 */
public class LibraryApp {
    private static Library lib = new Library();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Library Management System (Console) ===");
        mainMenu();
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1) Admin Login");
            System.out.println("2) Member Login");
            System.out.println("3) Register Member");
            System.out.println("4) Exit");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": adminLogin(); break;
                case "2": memberLogin(); break;
                case "3": registerMember(); break;
                case "4":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- admin ----------
    private static void adminLogin() {
        System.out.print("Admin username: ");
        String u = sc.nextLine();
        System.out.print("Admin password: ");
        String p = sc.nextLine();
        if (lib.authenticateAdmin(u, p)) {
            System.out.println("Admin authenticated.");
            adminMenu();
        } else {
            System.out.println("Wrong admin credentials.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nADMIN MENU:");
            System.out.println("1) Add Book");
            System.out.println("2) Update Book");
            System.out.println("3) Delete Book");
            System.out.println("4) List Books");
            System.out.println("5) List Members");
            System.out.println("6) Delete Member");
            System.out.println("7) View Transactions");
            System.out.println("8) Reports Summary");
            System.out.println("9) Back to Main");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": addBook(); break;
                case "2": updateBook(); break;
                case "3": deleteBook(); break;
                case "4": listBooks(); break;
                case "5": listMembers(); break;
                case "6": deleteMember(); break;
                case "7": viewTransactions(); break;
                case "8": reportsSummary(); break;
                case "9": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        System.out.print("Category: "); String cat = sc.nextLine();
        System.out.print("Copies: "); int copies = Integer.parseInt(sc.nextLine());
        Book b = new Book(title, author, cat, copies);
        lib.addBook(b);
        System.out.println("Book added: " + b);
    }

    private static void updateBook() {
        listBooks();
        System.out.print("Enter book id to update: ");
        String id = sc.nextLine();
        Book b = lib.findBookById(id);
        if (b == null) { System.out.println("Not found."); return; }
        System.out.print("New title (enter to skip): ");
        String t = sc.nextLine(); if (!t.isEmpty()) b.setTitle(t);
        System.out.print("New author (enter to skip): ");
        String a = sc.nextLine(); if (!a.isEmpty()) b.setAuthor(a);
        System.out.print("New category (enter to skip): ");
        String c = sc.nextLine(); if (!c.isEmpty()) b.setCategory(c);
        System.out.print("New total copies (enter to skip): ");
        String copies = sc.nextLine(); if (!copies.isEmpty()) b.setTotalCopies(Integer.parseInt(copies));
        System.out.println("Book updated: " + b);
    }

    private static void deleteBook() {
        listBooks();
        System.out.print("Enter book id to delete: ");
        String id = sc.nextLine();
        if (lib.deleteBook(id)) System.out.println("Deleted.");
        else System.out.println("Not found or cannot delete.");
    }

    private static void listBooks() {
        System.out.println("\nBooks in library:");
        for (Book b : lib.listBooks()) System.out.println(b);
    }

    private static void listMembers() {
        System.out.println("\nMembers:");
        for (Member m : lib.listMembers()) System.out.println(m);
    }

    private static void deleteMember() {
        listMembers();
        System.out.print("Enter username to delete: ");
        String u = sc.nextLine();
        if (lib.deleteMember(u)) System.out.println("Deleted.");
        else System.out.println("Not found.");
    }

    private static void viewTransactions() {
        System.out.println("\nTransactions:");
        for (Transaction t : lib.listTransactions()) System.out.println(t);
    }

    private static void reportsSummary() {
        Map<String, Object> r = lib.generateReportSummary();
        System.out.println("\n--- Report Summary ---");
        System.out.println("Total books: " + r.get("totalBooks"));
        System.out.println("Total members: " + r.get("totalMembers"));
        System.out.println("Total transactions: " + r.get("totalTransactions"));
        System.out.println("Currently issued: " + r.get("currentlyIssued"));
    }

    // ---------- member ----------
    private static void registerMember() {
        System.out.print("Choose username: "); String u = sc.nextLine();
        System.out.print("Choose password: "); String p = sc.nextLine();
        System.out.print("Your name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        Member m = lib.registerMember(u, p, name, email);
        if (m == null) System.out.println("Username exists, try another.");
        else System.out.println("Registered. Your member id: " + m.getId());
    }

    private static void memberLogin() {
        System.out.print("Username: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        Member m = lib.authenticateMember(u, p);
        if (m == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Welcome " + m.getName());
        memberMenu(m);
    }

    private static void memberMenu(Member m) {
        while (true) {
            System.out.println("\nMEMBER MENU:");
            System.out.println("1) Browse Books");
            System.out.println("2) Search Books");
            System.out.println("3) Issue Book");
            System.out.println("4) Return Book");
            System.out.println("5) Reserve Book");
            System.out.println("6) My Borrowed Books");
            System.out.println("7) My Transactions");
            System.out.println("8) Update Profile");
            System.out.println("9) Change Password");
            System.out.println("10) Logout");
            System.out.print("Choice: ");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1": listBooks(); break;
                case "2": searchBooks(); break;
                case "3": issueBook(m); break;
                case "4": returnBook(m); break;
                case "5": reserveBook(m); break;
                case "6": myBorrowedBooks(m); break;
                case "7": myTransactions(m); break;
                case "8": updateProfile(m); break;
                case "9": changePassword(m); break;
                case "10": return;
                default: System.out.println("Invalid.");
            }
        }
    }

    private static void searchBooks() {
        System.out.print("Enter title/author/category/id to search: ");
        String q = sc.nextLine();
        List<Book> res = lib.searchBooks(q);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    private static void issueBook(Member m) {
        listBooks();
        System.out.print("Enter book id to issue: ");
        String id = sc.nextLine();
        Book b = lib.findBookById(id);
        if (b == null) { System.out.println("No such book."); return; }
        String msg = lib.issueBook(m, b);
        System.out.println(msg);
    }

    private static void returnBook(Member m) {
        myBorrowedBooks(m);
        System.out.print("Enter book id to return: ");
        String id = sc.nextLine();
        Book b = lib.findBookById(id);
        if (b == null) { System.out.println("No such book."); return; }
        String msg = lib.returnBook(m, b);
        System.out.println(msg);
    }

    private static void reserveBook(Member m) {
        listBooks();
        System.out.print("Enter book id to reserve: ");
        String id = sc.nextLine();
        Book b = lib.findBookById(id);
        if (b == null) { System.out.println("No such book."); return; }
        String msg = lib.reserveBook(m, b);
        System.out.println(msg);
    }

    private static void myBorrowedBooks(Member m) {
        System.out.println("Your borrowed books:");
        if (m.getBorrowedBookIds().isEmpty()) {
            System.out.println("None");
            return;
        }
        for (String bid : m.getBorrowedBookIds()) {
            Book b = lib.findBookById(bid);
            System.out.println(b);
        }
    }

    private static void myTransactions(Member m) {
        List<Transaction> list = lib.transactionsForMember(m);
        if (list.isEmpty()) System.out.println("No transactions.");
        else list.forEach(System.out::println);
    }

    private static void updateProfile(Member m) {
        System.out.print("New name (enter to skip): "); String n = sc.nextLine();
        System.out.print("New email (enter to skip): "); String e = sc.nextLine();
        if (!n.isEmpty()) m.setName(n);
        if (!e.isEmpty()) m.setEmail(e);
        lib.updateMemberProfile(m, m.getName(), m.getEmail());
        System.out.println("Profile updated.");
    }

    private static void changePassword(Member m) {
        System.out.print("Old password: "); String oldp = sc.nextLine();
        System.out.print("New password: "); String newp = sc.nextLine();
        if (lib.changeMemberPassword(m, oldp, newp)) System.out.println("Password changed.");
        else System.out.println("Old password incorrect.");
    }
}
