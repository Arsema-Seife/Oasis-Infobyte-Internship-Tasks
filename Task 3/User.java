public class User {
    private String userId;
    private String pin;
    public Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(initialBalance);
    }

    public boolean authenticate(String id, String p) {
        return userId.equals(id) && pin.equals(p);
    }
}
