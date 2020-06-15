package trucking.model;

import javax.persistence.Entity;

@Entity
public class Manager extends Person {

    public Manager(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    protected Manager() { super(); }

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
