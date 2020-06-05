package trucking.model;

import trucking.repository.RepositoryItem;

import javax.persistence.Embedded;
import javax.persistence.Entity;

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
        Order order = contract.getOrder();
        order.setState(Order.State.PROCESSED);
    }

    // todo mb rename: this complete is not the same as Client's or Driver's complete
    public void completeContract(Contract contract) throws Exception {
        if (!contract.isCompleted()) {
            throw new Exception();
        }

        Order order = contract.getOrder();
        order.setState(Order.State.COMPLETED);
    }

}
