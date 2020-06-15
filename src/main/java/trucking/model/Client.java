package trucking.model;

import javax.persistence.Entity;

@Entity
public class Client extends ContractParticipant {

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email, Contract.ParticipantRole.CLIENT);
    }

    protected Client() {
        super(Contract.ParticipantRole.CLIENT);
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
