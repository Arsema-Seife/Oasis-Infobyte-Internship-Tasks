import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type { ISSUE, RETURN, RESERVATION }

    private String id;
    private Type type;
    private String memberId;
    private String bookId;
    private LocalDate date;       // action date
    private LocalDate dueDate;    // for issues
    private double fine;          // if any for return

    public Transaction(Type type, String memberId, String bookId, LocalDate date, LocalDate dueDate) {
        this.id = "T-" + UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.memberId = memberId;
        this.bookId = bookId;
        this.date = date;
        this.dueDate = dueDate;
        this.fine = 0.0;
    }

    public String getId() { return id; }
    public Type getType() { return type; }
    public String getMemberId() { return memberId; }
    public String getBookId() { return bookId; }
    public LocalDate getDate() { return date; }
    public LocalDate getDueDate() { return dueDate; }
    public double getFine() { return fine; }
    public void setFine(double f) { this.fine = f; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] %s | Member: %s | Book: %s | Date: %s", id, type, memberId, bookId, date.format(fmt)));
        if (dueDate != null) sb.append(" | Due: ").append(dueDate.format(fmt));
        if (fine > 0) sb.append(" | Fine: ").append(fine);
        return sb.toString();
    }
}
