package core.model;

import core.repository.RepositorySingleton;

import java.util.List;

public class Client extends Contract.Participant {

    private Contacts contacts;

    public Client(Contacts contacts) {
        super(Contract.ParticipantRole.CLIENT);
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void createOrder(Order order) {
        RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .add(order);
    }

    // can remove only unprocessed order
    public void removeOrder(Order order) throws Exception {
        if (order.getState() != Order.State.PUBLISHED) {
            throw new Exception();
        }

        RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .remove(order);
    }

    public List<Order> getOrders() {
        return RepositorySingleton
                .getInstance()
                .getOrderRepository()
                .getByClient(this);
    }

    public List<Contract> getContracts() {
        return RepositorySingleton
                .getInstance()
                .getContractRepository()
                .getByClient(this);
    }

}
