package trucking.model;

import trucking.repository.RepositoryItem;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public class Person extends RepositoryItem {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Person(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    protected Person() { }

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
