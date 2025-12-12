import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Library: manages books, members, transactions and persistence.
 */
public class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Member> members = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    // storage files
    private static final String BOOKS_FILE = "books.ser";
    private static final String MEMBERS_FILE = "members.ser";
    private static final String TX_FILE = "transactions.ser";

    // admin credentials (simple)
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    // issue policy
    private static final int ISSUE_DAYS = 14;      // due after 14 days
    private static final double FINE_PER_DAY = 1.0; // currency units per late day

    public Library() {
        loadAll();
        if (members.isEmpty()) seedDemoMembers();
        if (books.isEmpty()) seedDemoBooks();
    }

    // ----- persistence -----
    @SuppressWarnings("unchecked")
    private void loadAll() {
        // load books
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKS_FILE))) {
            books = (Map<String, Book>) ois.readObject();
        } catch (Exception e) {
            // ignore: file not found or first run
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MEMBERS_FILE))) {
            members = (Map<String, Member>) ois.readObject();
        } catch (Exception e) { }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TX_FILE))) {
            transactions = (List<Transaction>) ois.readObject();
        } catch (Exception e) { }
    }

    private void saveAll() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE))) {
            oos.writeObject(books);
        } catch (Exception e) { System.err.println("Error saving books: " + e.getMessage()); }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MEMBERS_FILE))) {
            oos.writeObject(members);
        } catch (Exception e) { System.err.println("Error saving members: " + e.getMessage()); }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TX_FILE))) {
            oos.writeObject(transactions);
        } catch (Exception e) { System.err.println("Error saving transactions: " + e.getMessage()); }
    }

    // ----- demo data -----
    private void seedDemoBooks() {
        addBook(new Book("Effective Java", "Joshua Bloch", "Programming", 3));
        addBook(new Book("Clean Code", "Robert C. Martin", "Programming", 2));
        addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fiction", 4));
        addBook(new Book("A Brief History of Time", "Stephen Hawking", "Science", 1));
        saveAll();
    }
    private void seedDemoMembers() {
        Member m = new Member("john", "pass", "John Doe", "john@example.com");
        members.put(m.getUsername(), m);
        Member m2 = new Member("alice", "alice123", "Alice Smith", "alice@example.com");
        members.put(m2.getUsername(), m2);
        saveAll();
    }

    // ----- admin check -----
    public boolean authenticateAdmin(String user, String pass) {
        return ADMIN_USER.equals(user) && ADMIN_PASS.equals(pass);
    }

    // ----- member management -----
    public Member registerMember(String username, String password, String name, String email) {
        if (members.containsKey(username)) return null;
        Member m = new Member(username, password, name, email);
        members.put(username, m);
        saveAll();
        return m;
    }

    public Member authenticateMember(String username, String password) {
        Member m = members.get(username);
        if (m != null && m.getPassword().equals(password)) return m;
        return null;
    }

    public boolean updateMemberProfile(Member m, String newName, String newEmail) {
        m.setName(newName);
        m.setEmail(newEmail);
        saveAll();
        return true;
    }

    public boolean changeMemberPassword(Member m, String oldPass, String newPass) {
        if (!m.getPassword().equals(oldPass)) return false;
        m.setPassword(newPass);
        saveAll();
        return true;
    }

    public List<Member> listMembers() {
        return new ArrayList<>(members.values());
    }

    public boolean deleteMember(String username) {
        Member removed = members.remove(username);
        if (removed != null) {
            saveAll();
            return true;
        }
        return false;
    }

    // ----- book management -----
    public Book addBook(Book b) {
        books.put(b.getId(), b);
        saveAll();
        return b;
    }

    public boolean updateBook(String bookId, String title, String author, String category, int copies) {
        Book b = books.get(bookId);
        if (b == null) return false;
        b.setTitle(title);
        b.setAuthor(author);
        b.setCategory(category);
        b.setTotalCopies(copies);
        saveAll();
        return true;
    }

    public boolean deleteBook(String bookId) {
        Book removed = books.remove(bookId);
        if (removed != null) {
            saveAll();
            return true;
        }
        return false;
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books.values());
    }

    public Book findBookById(String id) {
        return books.get(id);
    }

    public List<Book> searchBooks(String q) {
        String s = q.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(s)
                          || b.getAuthor().toLowerCase().contains(s)
                          || b.getCategory().toLowerCase().contains(s)
                          || b.getId().toLowerCase().contains(s))
                .collect(Collectors.toList());
    }

    // ----- issue / return / reservation -----
    public String issueBook(Member m, Book b) {
        // if available, issue
        if (b.getAvailableCopies() > 0) {
            b.issueOne();
            LocalDate now = LocalDate.now();
            LocalDate due = now.plusDays(ISSUE_DAYS);
            m.borrowBook(b.getId());
            Transaction tx = new Transaction(Transaction.Type.ISSUE, m.getId(), b.getId(), now, due);
            transactions.add(tx);
            saveAll();
            return "Issued successfully. Due date: " + due.toString();
        } else {
            // no copies -> suggest reservation
            return "No copies available. You can reserve the book.";
        }
    }

    public String returnBook(Member m, Book b) {
        // check if member actually borrowed
        if (!m.getBorrowedBookIds().contains(b.getId())) {
            return "This member did not borrow that book.";
        }
        // find the issue transaction to get due date
        Optional<Transaction> opt = transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.ISSUE
                        && t.getBookId().equals(b.getId())
                        && t.getMemberId().equals(m.getId()))
                .reduce((first, second) -> second); // get latest issue for this member/book

        LocalDate now = LocalDate.now();
        double fine = 0.0;
        if (opt.isPresent()) {
            Transaction issueTx = opt.get();
            LocalDate due = issueTx.getDueDate();
            if (due != null && now.isAfter(due)) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(due, now);
                fine = daysLate * FINE_PER_DAY;
            }
        }

        // process return
        b.returnOne();
        m.returnBook(b.getId());
        Transaction retTx = new Transaction(Transaction.Type.RETURN, m.getId(), b.getId(), now, null);
        retTx.setFine(fine);
        transactions.add(retTx);

        // if reservations exist, allocate to next member
        if (b.hasReservations()) {
            String nextMemberId = b.popReservation();
            // find member by id
            Member next = members.values().stream().filter(mem -> mem.getId().equals(nextMemberId)).findFirst().orElse(null);
            if (next != null) {
                // auto-issue to reserved member if copies now available
                if (b.getAvailableCopies() > 0) {
                    b.issueOne();
                    next.borrowBook(b.getId());
                    Transaction auto = new Transaction(Transaction.Type.ISSUE, next.getId(), b.getId(), LocalDate.now(), LocalDate.now().plusDays(ISSUE_DAYS));
                    transactions.add(auto);
                    saveAll();
                    return String.format("Returned successfully. Fine: %.2f. Book auto-issued to reserver: %s (username).", fine, next.getUsername());
                } else {
                    saveAll();
                    return String.format("Returned successfully. Fine: %.2f. Book was reserved by %s but no copies available to auto-issue.", fine, next.getUsername());
                }
            }
        }

        saveAll();
        if (fine > 0) return String.format("Returned successfully. Fine: %.2f", fine);
        return "Returned successfully. No fine.";
    }

    public String reserveBook(Member m, Book b) {
        if (b.getAvailableCopies() > 0) {
            return "Book is available now; no need to reserve. You can issue it.";
        }
        b.addReservation(m.getId());
        Transaction tx = new Transaction(Transaction.Type.RESERVATION, m.getId(), b.getId(), LocalDate.now(), null);
        transactions.add(tx);
        saveAll();
        return "Reserved successfully. You will be notified when a copy becomes available.";
    }

    // ----- transactions & reports -----
    public List<Transaction> listTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> transactionsForMember(Member m) {
        return transactions.stream().filter(t -> t.getMemberId().equals(m.getId())).collect(Collectors.toList());
    }

    public List<Transaction> transactionsForBook(Book b) {
        return transactions.stream().filter(t -> t.getBookId().equals(b.getId())).collect(Collectors.toList());
    }

    public List<Book> booksByCategory(String cat) {
        return books.values().stream().filter(b -> b.getCategory().equalsIgnoreCase(cat)).collect(Collectors.toList());
    }

    public Map<String, Object> generateReportSummary() {
        Map<String, Object> r = new HashMap<>();
        r.put("totalBooks", books.size());
        r.put("totalMembers", members.size());
        r.put("totalTransactions", transactions.size());
        long issuedNow = members.values().stream().mapToLong(m -> m.getBorrowedBookIds().size()).sum();
        r.put("currentlyIssued", issuedNow);
        return r;
    }
}
