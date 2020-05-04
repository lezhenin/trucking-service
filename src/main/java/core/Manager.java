package core;

import core.repository.RepositorySingleton;

import java.util.List;

public class Manager {

    private Contacts contacts;

    public Manager(Contacts contacts) {
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public List<Order> getOrders() {
        return RepositorySingleton.getInstance().getOrderRepository().getAll();
    }


}
