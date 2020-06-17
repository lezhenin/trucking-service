package trucking.model;

import javax.persistence.Entity;

@Entity
public class Client extends Contractor {

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email, Contract.Role.CLIENT);
    }

    protected Client() {
        super(Contract.Role.CLIENT);
    }


    public void createOrder(Order order) {
    }

    // can remove only unprocessed order
    public void removeOrder(Order order) throws Exception {
        if (order.getState() != Order.State.PUBLISHED) {
            throw new Exception();
        }
    }

}
