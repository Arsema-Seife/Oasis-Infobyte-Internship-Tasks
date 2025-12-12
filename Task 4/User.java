public class User {
    private String username;
    private String password;
    private String name;
    private String email;

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean authenticate(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
        System.out.println("Profile updated successfully!");
    }

    public void updatePassword(String oldPass, String newPass) {
        if (this.password.equals(oldPass)) {
            this.password = newPass;
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Incorrect old password.");
        }
    }

    public String getName() { return name; }
}
