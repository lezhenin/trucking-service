package core.model;

import core.repository.RepositoryItem;
import core.repository.RepositorySingleton;

import java.util.List;

public class Manager extends RepositoryItem {

    private final Contacts contacts;

    public Manager(Contacts contacts) {
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void createContract(Contract contract) {

        Order order = contract.getOrder();
        order.setState(Order.OrderState.PROCESSED);

        RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .update(order);

        RepositorySingleton
                .getInstance()
                .getContractRepository()
                .add(contract);

    }

    public List<Order> getOrders() {
        return RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .getAll();
    }

    public List<Driver> getDrivers () {
        return RepositorySingleton
                .getInstance()
                .getDriverRepository()
                .getAll();
    }


}
