package core;

import core.repository.RepositorySingleton;

import java.util.List;

public class Client {

    private Contacts contacts;

    public Client(Contacts contacts) {
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public List<Order> getOrders() {
        return RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .getByClient(this);
    }

}
