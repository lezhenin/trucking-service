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
        order.setState(Order.State.PROCESSED);

        RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .update(order);

        RepositorySingleton
                .getInstance()
                .getContractRepository()
                .add(contract);

    }

    // todo mb rename: this complete is not the same as Client's or Driver's complete
    public void completeContract(Contract contract) throws Exception {
        if (!contract.isComplete()) {
            throw new Exception();
        }
        contract.getOrder().setState(Order.State.COMPLETED);
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
