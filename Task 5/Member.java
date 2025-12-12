import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;      // M-xxxx
    private String username;
    private String password;
    private String name;
    private String email;

    // borrowed book records: bookId -> dueDate stored in transactions; here we keep list of currently borrowed book ids
    private List<String> borrowedBookIds = new ArrayList<>();

    public Member(String username, String password, String name, String email) {
        this.id = "M-" + UUID.randomUUID().toString().substring(0, 8);
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<String> getBorrowedBookIds() { return borrowedBookIds; }

    public void setName(String n) { this.name = n; }
    public void setEmail(String e) { this.email = e; }
    public void setPassword(String p) { this.password = p; }

    public void borrowBook(String bookId) { borrowedBookIds.add(bookId); }
    public void returnBook(String bookId) { borrowedBookIds.remove(bookId); }

    @Override
    public String toString() {
        return String.format("%s (%s) — %s | %s | Borrowed: %d",
                name, id, username, email, borrowedBookIds.size());
    }
}
