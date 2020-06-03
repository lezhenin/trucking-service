package trucking.core.model;

import javax.persistence.Embeddable;

@Embeddable
public class Contacts {

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Contacts(String username, String firstName, String lastName, String phoneNumber, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    protected Contacts() { }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
