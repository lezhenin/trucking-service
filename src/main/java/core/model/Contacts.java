package core.model;

import core.repository.RepositoryItem;

public class Contacts extends RepositoryItem {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Contacts(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
}
