package core.model;

import core.repository.RepositoryItem;
import core.repository.RepositorySingleton;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Client extends RepositoryItem {

    @Embedded private Contacts contacts;

    public Client(Contacts contacts) {
        this.contacts = contacts;
    }

    protected Client() { }

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

    public void approveContract(Contract contract) throws Exception {
        if (!contract.getClient().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getClientStatus() != Contract.Status.NONE) {
            throw new Exception();
        }
        contract.setClientStatus(Contract.Status.APPROVED);
    }

    public void refuseContract(Contract contract) throws Exception {
        if (!contract.getClient().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getClientStatus() != Contract.Status.NONE) {
            throw new Exception();
        }
        contract.setClientStatus(Contract.Status.REFUSED);
    }

    public void completeContract(Contract contract) throws Exception {
        if (!contract.getClient().getId().equals(this.getId())) {
            throw new Exception();
        }
        if (contract.getClientStatus() == Contract.Status.NONE ||
                contract.getClientStatus() == Contract.Status.REFUSED) {
            throw new Exception();
        }
        if (contract.getDriverStatus() == Contract.Status.NONE ||
                contract.getDriverStatus() == Contract.Status.REFUSED) {
            throw new Exception();
        }
        if (contract.getClientStatus() == Contract.Status.COMPLETED) {
            throw new Exception();
        }
        contract.setClientStatus(Contract.Status.COMPLETED);
    }
}
