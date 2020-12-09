package trucking.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Manager extends Person {

    public Manager(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    protected Manager() { super(); }

    public void createContract(Contract contract) throws Exception {

        Order order = contract.getOrder();
        Offer offer = order.getOffer();
        Driver driver = contract.getDriver();


        if (offer == null) {
            throw new Exception("There is no accepted offer");
        }

        if (!Objects.equals(driver.getId(), offer.getDriver().getId())) {
            throw new Exception("Driver's offer wasn't accepted");
        }

        if (offer.getPrice() > contract.getPayment()) {
            throw new Exception("The payment is lower than was offered");
        }

        if (order.getState() != Order.State.PUBLISHED) {
            throw new Exception("Order already has been processed/completed");
        }

        if (order.getState() == Order.State.REMOVED) {
            throw new Exception("Order already has been removed");
        }

        Vehicle vehicle = driver.getVehicle();
        if (order.getCargoHeight() > vehicle.getMaxCargoHeight() ||
                order.getCargoWidth() > vehicle.getMaxCargoWidth() ||
                order.getCargoLength() > vehicle.getMaxCargoLength() ||
                order.getCargoWeight() > vehicle.getMaxCargoWeight()) {
            throw new Exception("Cargo doesn't fit driver's vehicle");
        }

        order.setState(Order.State.PROCESSED);
    }

    public void removeContract(Contract contract) throws Exception {
        if (!contract.isRefused()) {
            throw new Exception("Contract haven't been refused");
        }
        Order order = contract.getOrder();
        order.setState(Order.State.PUBLISHED);
    }

    public void completeContract(Contract contract) throws Exception {

        if (!Objects.equals(contract.getManager().getId(), getId())) {
            throw new Exception("Manager is not associated with contract");
        }

        if (!contract.isCompleted()) {
            throw new Exception("Contractors haven't completed contract yet");
        }

        Order order = contract.getOrder();
        order.setState(Order.State.COMPLETED);
    }

}
