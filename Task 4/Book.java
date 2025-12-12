import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;          // unique id
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;

    // queue of memberIds who reserved the book (advance booking)
    private Deque<String> reservationQueue = new ArrayDeque<>();

    public Book(String title, String author, String category, int copies) {
        this.id = "B-" + UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public void setTitle(String t) { this.title = t; }
    public void setAuthor(String a) { this.author = a; }
    public void setCategory(String c) { this.category = c; }
    public void setTotalCopies(int c) {
        int issued = totalCopies - availableCopies;
        this.totalCopies = c;
        this.availableCopies = Math.max(0, c - issued);
    }

    public boolean issueOne() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void
