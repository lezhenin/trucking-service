package trucking.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Client extends Contractor {

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email, Contract.Role.CLIENT);
    }

    protected Client() {
        super(Contract.Role.CLIENT);
    }


    public void createOrder(Order order) throws Exception {
        if (order.getState() != Order.State.PUBLISHED) {
            throw new ModelException("Order already has been processed");
        }
    }

    public void removeOrder(Order order) throws Exception {
        if (order.getState() != Order.State.PUBLISHED) {
            throw new ModelException("Order already has been processed");
        }
        order.setState(Order.State.REMOVED);
    }


    public void acceptOffer(Offer offer) throws Exception {

        Order order = offer.getOrder();

        if (!Objects.equals(order.getClient().getId(), getId())) {
            throw new ModelException("Client is not associated with order");
        }

        if (order.hasAcceptedOffer()) {
            throw new ModelException("There is already accepted offer");
        }

        order.setOffer(offer);
        offer.setAccepted(true);

    }

}
