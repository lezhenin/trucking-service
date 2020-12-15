package trucking.web.datatransfer;

public class UserData {
    private String username;
    private String role;

    public UserData(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
