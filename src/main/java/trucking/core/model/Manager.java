package trucking.core.model;

import trucking.core.repository.RepositoryItem;
import trucking.core.repository.RepositorySingleton;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Manager extends RepositoryItem {

    @Embedded private Contacts contacts;

    public Manager(Contacts contacts) {
        this.contacts = contacts;
    }

    protected Manager() { }

    public Contacts getContacts() {
        return contacts;
    }

    public void createContract(Contract contract) {

        RepositorySingleton.getInstance()
                .getContractRepository()
                .save(contract);

        Order order = contract.getOrder();
        order.setState(Order.State.PROCESSED);

        RepositorySingleton.getInstance()
                .getOrderRepository()
                .save(order);

    }

    // todo mb rename: this complete is not the same as Client's or Driver's complete
    public void completeContract(Contract contract) throws Exception {
        if (!contract.isCompleted()) {
            throw new Exception();
        }

        Order order = contract.getOrder();
        order.setState(Order.State.COMPLETED);
        RepositorySingleton.getInstance()
                .getOrderRepository()
                .save(order);

    }

    public List<Order> getOrders() {
        return RepositorySingleton.getInstance()
                .getOrderRepository()
                .findAll();
    }

    public List<Driver> getDrivers () {
        return RepositorySingleton.getInstance()
                .getDriverRepository()
                .findAll();
    }


}
