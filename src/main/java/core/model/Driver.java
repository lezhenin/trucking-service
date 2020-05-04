package core.model;

import core.repository.RepositoryItem;

public class Driver extends RepositoryItem {

    private Contacts contacts;
    private Vehicle vehicle;

    public Driver(Contacts contacts, Vehicle vehicle) {
        this.contacts = contacts;
        this.vehicle = vehicle;
    }

//    public

    public Contacts getContacts() {
        return contacts;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
